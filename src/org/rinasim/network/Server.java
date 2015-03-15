package org.rinasim.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import org.rinasim.database.FrdDao;
import org.rinasim.database.UserDao;
import org.rinasim.frame.ServerUI;
import org.rinasim.object.User;
import org.rinasim.util.Identifier;
import org.rinasim.util.Time;

/**
 * 服务器
 * @author 刘旭涛
 * @date 2015年3月15日 下午8:38:25
 * @since v1.0
 */
public class Server extends Thread{

	/**
	 * 用户配对
	 */
	public static Map<Integer, ServerThread> clients=new HashMap<Integer, ServerThread>();
	
	private static ServerSocket server;
	
	/**
	 * 初始化服务器
	 * @param port 端口号
	 * @throws BindException,IOException 
	 */
	public Server(int port) throws BindException,IOException{
		server=new ServerSocket(port);
		ServerUI.serverInfo.append("("+Time.getDateTime()+")成功创建连接!\n");
		start();
		ServerUI.serverInfo.setCaretPosition(ServerUI.serverInfo.getText().length());
	}
	
	/**
	 * 等待连接
	 */
	public void run(){
		while(true){
			ServerUI.serverInfo.append("("+Time.getDateTime()+")正在等待连接...\n");
			ServerUI.serverInfo.setCaretPosition(ServerUI.serverInfo.getText().length());
			try {
				Socket socket=server.accept();
				socket.setTcpNoDelay(true);
				ObjectInputStream reader=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				ObjectOutputStream writer=new ObjectOutputStream(socket.getOutputStream());
				int identifier=reader.readInt();
				if(identifier==Identifier.LOGIN){
					int id=reader.readInt();
					String paswd=reader.readUTF();
					if(UserDao.login(id, paswd)){
						if(clients.get(id)!=null){
							writer.writeBoolean(false);
							writer.flush();
							writer.close();
							socket.close();
							ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"登陆失败，ID："+id+"\n");
						}else{
							writer.writeBoolean(true);
							writer.flush();
							UserDao.setStatus(id, true);
							ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"已连接，ID："+id+"\n");
							ServerThread thread=new ServerThread(id,socket,writer,reader);
							clients.put(id, thread);
							thread.start();
						}
					}else{
						writer.writeBoolean(false);
						writer.flush();
						writer.close();
						socket.close();
						ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"登陆失败，ID："+id+"\n");
					}
				}else if(identifier==Identifier.REGISTER){
					User user=(User) reader.readObject();
					boolean flag=UserDao.addUser(user);
					if(flag){
						FrdDao.addTable(Integer.parseInt(user.getId()));
						ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"已注册，ID："+user.getId()+"\n");
					}else{
						ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"注册失败，ID："+user.getId()+"\n");
					}
					writer.writeBoolean(flag);
					writer.flush();
					writer.close();
					socket.close();
				}
				ServerUI.serverInfo.setCaretPosition(ServerUI.serverInfo.getText().length());
			} catch (IOException e) {
				ServerUI.serverInfo.append("("+Time.getDateTime()+")服务器已关闭。\n");
				ServerUI.serverInfo.setCaretPosition(ServerUI.serverInfo.getText().length());
				break;
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭服务器
	 */
	public void close(){
		try {
			server.close();
			for(Thread e:clients.values()){
				e.interrupt();
			}
			clients.clear();
			interrupt();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 公告
	 * @param msg 向全体用户发出的消息
	 */
	public synchronized void broadCast(String msg){
		Iterator<ServerThread> iterator=clients.values().iterator();
		String time=Time.getDate();
		while(iterator.hasNext()){
			ObjectOutputStream writer=iterator.next().getObjectOutputStream();
			try {
				writer.writeInt(Identifier.SERVER);
				writer.writeUTF(time);
				writer.writeUTF(msg);
				writer.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "发生错误！\n"+e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * 推送更新
	 * @param version 版本号
	 */
	public synchronized void pushUpdate(String version){
		Iterator<ServerThread> iterator=clients.values().iterator();
		String time=Time.getDate();
		while(iterator.hasNext()){
			ObjectOutputStream writer=iterator.next().getObjectOutputStream();
			try {
				writer.writeInt(Identifier.PUSH_UPDATE);
				writer.writeUTF(time);
				writer.writeUTF(version);
				writer.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "发生错误！\n"+e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

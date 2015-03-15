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
 * ������
 * @author ������
 * @date 2015��3��15�� ����8:38:25
 * @since v1.0
 */
public class Server extends Thread{

	/**
	 * �û����
	 */
	public static Map<Integer, ServerThread> clients=new HashMap<Integer, ServerThread>();
	
	private static ServerSocket server;
	
	/**
	 * ��ʼ��������
	 * @param port �˿ں�
	 * @throws BindException,IOException 
	 */
	public Server(int port) throws BindException,IOException{
		server=new ServerSocket(port);
		ServerUI.serverInfo.append("("+Time.getDateTime()+")�ɹ���������!\n");
		start();
		ServerUI.serverInfo.setCaretPosition(ServerUI.serverInfo.getText().length());
	}
	
	/**
	 * �ȴ�����
	 */
	public void run(){
		while(true){
			ServerUI.serverInfo.append("("+Time.getDateTime()+")���ڵȴ�����...\n");
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
							ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"��½ʧ�ܣ�ID��"+id+"\n");
						}else{
							writer.writeBoolean(true);
							writer.flush();
							UserDao.setStatus(id, true);
							ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"�����ӣ�ID��"+id+"\n");
							ServerThread thread=new ServerThread(id,socket,writer,reader);
							clients.put(id, thread);
							thread.start();
						}
					}else{
						writer.writeBoolean(false);
						writer.flush();
						writer.close();
						socket.close();
						ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"��½ʧ�ܣ�ID��"+id+"\n");
					}
				}else if(identifier==Identifier.REGISTER){
					User user=(User) reader.readObject();
					boolean flag=UserDao.addUser(user);
					if(flag){
						FrdDao.addTable(Integer.parseInt(user.getId()));
						ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"��ע�ᣬID��"+user.getId()+"\n");
					}else{
						ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"ע��ʧ�ܣ�ID��"+user.getId()+"\n");
					}
					writer.writeBoolean(flag);
					writer.flush();
					writer.close();
					socket.close();
				}
				ServerUI.serverInfo.setCaretPosition(ServerUI.serverInfo.getText().length());
			} catch (IOException e) {
				ServerUI.serverInfo.append("("+Time.getDateTime()+")�������ѹرա�\n");
				ServerUI.serverInfo.setCaretPosition(ServerUI.serverInfo.getText().length());
				break;
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	/**
	 * �رշ�����
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
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * ����
	 * @param msg ��ȫ���û���������Ϣ
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
				JOptionPane.showMessageDialog(null, "��������\n"+e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * ���͸���
	 * @param version �汾��
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
				JOptionPane.showMessageDialog(null, "��������\n"+e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

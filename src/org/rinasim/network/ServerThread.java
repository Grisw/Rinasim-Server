package org.rinasim.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.rinasim.database.FrdDao;
import org.rinasim.database.UserDao;
import org.rinasim.frame.ServerUI;
import org.rinasim.object.Message;
import org.rinasim.object.User;
import org.rinasim.util.FileOperator;
import org.rinasim.util.Identifier;
import org.rinasim.util.Time;
import org.rinasim.widget.FriendPanel;

/**
 * 服务器监听线程
 * @author 刘旭涛
 * @date 2015年3月15日 下午9:11:09
 * @since v1.0
 */
public class ServerThread extends Thread{

	private Socket socket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private int id;
	private Map<Integer, List<Message>> offlineMsg=new HashMap<Integer, List<Message>>();
	
	/**
	 * 初始化线程
	 * @param socket 连接客户的套接字
	 */
	public ServerThread(int id,Socket socket,ObjectOutputStream writer,ObjectInputStream reader){
		this.socket=socket;
		this.id=id;
		this.reader=reader;
		this.writer=writer;
	}
	
	/**
	 * 监听客户信息
	 */
	public void run(){
		try {
			while(true){
				int identifier=reader.readInt();
				if(identifier==Identifier.MSG){
					int id=reader.readInt();
					try {
						Message msg=(Message) reader.readObject();
						if(Server.clients.containsKey(id)){
							Server.clients.get(id).getObjectOutputStream().writeInt(identifier);
							Server.clients.get(id).getObjectOutputStream().writeInt(this.id);
							Server.clients.get(id).getObjectOutputStream().writeObject(msg);
							Server.clients.get(id).getObjectOutputStream().flush();
							FrdDao.setHasMessage(id, this.id, true);
							if(offlineMsg.containsKey(id)){
								List<Message> tmp=offlineMsg.get(id);
								tmp.add(msg);
								offlineMsg.put(id, tmp);
							}else{
								List<Message> tmp=new ArrayList<Message>();
								tmp.add(msg);
								offlineMsg.put(id, tmp);
							}
							FrdDao.setOfflineMsg(id, this.id, offlineMsg.get(id));
						}else{
							if(offlineMsg.containsKey(id)){
								List<Message> tmp=offlineMsg.get(id);
								tmp.add(msg);
								offlineMsg.put(id, tmp);
							}else{
								List<Message> tmp=new ArrayList<Message>();
								tmp.add(msg);
								offlineMsg.put(id, tmp);
							}
							FrdDao.setHasMessage(id, this.id, true);
							FrdDao.setOfflineMsg(id, this.id, offlineMsg.get(id));
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "发生错误！\n"+e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
					}
				}else if(identifier==Identifier.ADD_FRIEND){
					int frdId=reader.readInt();
					boolean flag=FrdDao.appendFrd(id, frdId);
					writer.writeInt(identifier);
					writer.writeBoolean(flag);
					writer.flush();
				}else if(identifier==Identifier.DELETE_FRIEND){
					int frdId=reader.readInt();
					boolean flag=FrdDao.deleteFrd(id, frdId);
					writer.writeInt(identifier);
					writer.writeBoolean(flag);
					writer.flush();
				}else if(identifier==Identifier.EDIT_PASSWORD){
					String oldPasw=reader.readUTF();
					String newPasw=reader.readUTF();
					boolean flag=UserDao.editPasw(id, oldPasw.toString(), newPasw);
					writer.writeInt(identifier);
					writer.writeBoolean(flag);
					writer.flush();
				}else if(identifier==Identifier.EDIT_USER_INFO){
					boolean flag=false;
					try {
						flag=UserDao.editUser((User) reader.readObject());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
					}
					writer.writeInt(identifier);
					writer.writeBoolean(flag);
					writer.flush();
				}else if(identifier==Identifier.GET_FRIEND_TREE){
					List<FriendPanel> model=FrdDao.getFrdPanel(id);
					writer.writeInt(identifier);
					writer.writeObject(model);
					writer.flush();
				}else if(identifier==Identifier.GET_USER_INFO){
					int target=reader.readInt();
					User user=UserDao.getUser(target);
					writer.writeInt(identifier);
					writer.writeObject(user);
					writer.flush();
				}else if(identifier==Identifier.PUSH_UPDATE){
					if(FileOperator.getUpdatePath()==null){
						writer.writeInt(identifier);
						writer.writeUTF("pc");
						writer.writeUTF("Unknown");
						writer.writeUTF("Unknown");
						writer.flush();
						continue;
					}
					String path=FileOperator.getUpdatePath().getMessage();
					String time=FileOperator.getUpdatePath().getTime();
					String version=path.substring(path.lastIndexOf("\\"), path.lastIndexOf("."));
					writer.writeInt(identifier);
					writer.writeUTF("pc");
					writer.writeUTF(time);
					writer.writeUTF(version);
					writer.flush();
				}else if(identifier==Identifier.UPDATE){
					if(FileOperator.getUpdatePath()==null){
						writer.writeInt(identifier);					
						writer.writeInt(0);
						writer.flush();
						continue;
					}
					String path=FileOperator.getUpdatePath().getMessage();
					byte[] data=new byte[(int) new File(path).length()];
					try {
						FileInputStream fis=new FileInputStream(path);
						fis.read(data);
						fis.close();
					} catch (IOException e){
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
					}
					writer.writeInt(identifier);					
					writer.writeInt((int) new File(path).length());
					if((int) new File(path).length()!=0){
						writer.writeUTF(path.substring(path.lastIndexOf("\\")));
						writer.write(data);
					}
					writer.flush();
				}else if(identifier==Identifier.SERVER){
					if(FileOperator.getBroadCast()==null){
						writer.writeInt(identifier);					
						writer.writeUTF("Unknown");
						writer.writeUTF("Unknown");
						writer.flush();
						continue;
					}
					String time=FileOperator.getBroadCast().getTime();
					String content=FileOperator.getBroadCast().getMessage();
					writer.writeInt(identifier);
					writer.writeUTF(time);
					writer.writeUTF(content);
					writer.flush();
				}else if(identifier==Identifier.OFFLINE_MESSAGE){
					int frdId=reader.readInt();
					List<Message> list=FrdDao.getOfflineMsg(id, frdId);
					writer.writeInt(identifier);
					writer.writeObject(list);
					writer.flush();
				}else if(identifier==Identifier.GET_FRIEND_STATUS){
					Map<Integer, Boolean> map=FrdDao.getFrdStatus(id);
					writer.writeInt(identifier);
					writer.writeObject(map);
					writer.flush();
				}else if(identifier==Identifier.SET_FRIEND_NOTE){
					int frdId=reader.readInt();
					String note=reader.readUTF();
					boolean flag=FrdDao.setFrdNote(id, frdId, note);
					writer.writeInt(identifier);
					writer.writeBoolean(flag);
					writer.flush();
				}else if(identifier==Identifier.READ_MESSAGE){
					int frdId=reader.readInt();
					FrdDao.setHasMessage(id, frdId, false);
					FrdDao.setOfflineMsg(id, frdId, null);
					if(Server.clients.containsKey(frdId)){
						Server.clients.get(frdId).getOfflineMessage().remove(id);
					}
				}
			}
		} catch (IOException e){
			UserDao.setStatus(id, false);
			Server.clients.remove(id);
			ServerUI.serverInfo.append("("+Time.getDateTime()+")"+socket.getInetAddress().getHostAddress()+"连接断开！ID:"+id+"\n");
			ServerUI.serverInfo.setCaretPosition(ServerUI.serverInfo.getText().length());
		}finally{
			try {
				if(socket!=null)socket.close();
				if(reader!=null)reader.close();
				if(writer!=null)writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * 获取输出流
	 * @return
	 */
	public ObjectOutputStream getObjectOutputStream(){
		return writer;
	}
	
	/**
	 * 获取离线消息
	 * @return
	 */
	public Map<Integer, List<Message>> getOfflineMessage(){
		return offlineMsg;
	}
}

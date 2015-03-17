package org.rinasim.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import org.rinasim.object.ServerMessage;

/**
 * �ļ�����
 * @date 2015��3��17�� ����10:16:04
 * @since v1.0
 */
public class FileOperator {

	private static final String PASSWORD_PATH=".\\data\\password.dat";
	private static final String UPDATE_PATH=".\\data\\update_path.dat";
	private static final String PORT_PATH=".\\data\\port.dat";
	private static final String BROADCAST_PATH=".\\data\\broadcast.dat";
	
	/**
	 * ��ȡ����
	 * @return ���ܺ������
	 */
	public static char[] getPassword(){
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(PASSWORD_PATH));
			return (char[]) reader.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	/**
	 * �������
	 * @param password ���ܺ������
	 */
	public static void writePassword(char[] password){
		ObjectOutputStream writer=null;
		File file=new File(PASSWORD_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeObject(password);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	/**
	 * ��ȡ�˿ں�
	 * @return �˿ں�
	 * @throws IOException 
	 */
	public static int getPort() throws IOException{
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(PORT_PATH));
			return reader.readInt();
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	/**
	 * ����˿���Ϣ
	 * @param port �˿ں�
	 */
	public static void writePort(int port){
		ObjectOutputStream writer=null;
		File file=new File(PORT_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeInt(port);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	/**
	 * ��ȡPC������Ϣ
	 * @return ����ʱ�䣬+���°汾�ļ�·��
	 */
	public static ServerMessage getUpdatePath(){
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(UPDATE_PATH));
			return new ServerMessage(reader.readUTF(), reader.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return null;
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	/**
	 * ���������Ϣ
	 * @param msg ������Ϣ
	 */
	public static void writeUpdate(ServerMessage msg){
		ObjectOutputStream writer=null;
		File file=new File(UPDATE_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeObject(msg);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
		}
	}

	/**
	 * ��ȡ����
	 * @return ʱ��+����
	 * @throws IOException 
	 */
	public static ServerMessage getBroadCast() throws IOException{
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(BROADCAST_PATH));
			return new ServerMessage(reader.readUTF(), reader.readUTF());
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	/**
	 * ���������Ϣ
	 * @param msg ����
	 */
	public static void writeBroadCast(ServerMessage msg){
		ObjectOutputStream writer=null;
		File file=new File(BROADCAST_PATH);
		try {
			if(!file.isFile())
				file.createNewFile();
			writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeObject(msg);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
		} finally {
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
}

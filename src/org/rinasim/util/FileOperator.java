package org.rinasim.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JOptionPane;

/**
 * �ļ�����
 * @author ������
 * @date 2015��3��15�� ����12:07:17
 * @since v1.0
 */
public class FileOperator {

	private static final String PASSWORD_PATH=".\\password.dat";
	
	/**
	 * ��ȡ����
	 * @author ������
	 * @date 2015��3��15�� ����12:11:26
	 * @since v1.0
	 * @return ���ܺ�������ַ�����
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
	
	public static void writePassword(char[] passwordArray){
		
	}

}

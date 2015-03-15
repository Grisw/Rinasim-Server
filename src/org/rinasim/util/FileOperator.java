package org.rinasim.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JOptionPane;

/**
 * 文件操作
 * @author 刘旭涛
 * @date 2015年3月15日 下午12:07:17
 * @since v1.0
 */
public class FileOperator {

	private static final String PASSWORD_PATH=".\\password.dat";
	
	/**
	 * 获取密码
	 * @author 刘旭涛
	 * @date 2015年3月15日 下午12:11:26
	 * @since v1.0
	 * @return 加密后的密码字符数组
	 */
	public static char[] getPassword(){
		ObjectInputStream reader=null;
		try {
			reader=new ObjectInputStream(new FileInputStream(PASSWORD_PATH));
			return (char[]) reader.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		} finally {
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	public static void writePassword(char[] passwordArray){
		
	}

}

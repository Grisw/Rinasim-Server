package org.rinasim.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * �������ݿ�
 * @author MissingNo.
 * @date 2014��12��15�� ����9:06:49
 * @version v3.0
 */
public class DataBase {	
	
	private static String user="sa";
	private static String password="MissingNo.";
	private static String className="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url="jdbc:sqlserver://localhost:1433;DatabaseName=Rinasim";
	
	/**
	 * ��ȡ����
	 * @return �����ݿ�����Ӷ���
	 */
	public static Connection getConnection(){
		try {
			Class.forName(className);
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "�������ݿ�ʧ�ܣ�\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "�������ݿ�ʧ�ܣ�\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}
	
}

package org.rinasim.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * 连接数据库
 * @date 2015年3月15日 上午10:14:38
 * @since v1.0
 */
public class DataBase {	
	
	private static String user="sa";
	private static String password="MissingNo.";
	private static String className="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url="jdbc:sqlserver://localhost:1433;DatabaseName=Rinasim";
	
	/**
	 * 获取连接
	 * @return 与数据库的连接对象
	 */
	public static Connection getConnection(){
		try {
			Class.forName(className);
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "连接数据库失败！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "连接数据库失败！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}
	
}

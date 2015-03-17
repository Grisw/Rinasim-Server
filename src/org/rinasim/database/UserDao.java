package org.rinasim.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.rinasim.object.User;

/**
 * 用户数据连接
 * @date 2015年3月15日 下午8:42:55
 * @since v1.0
 */
public class UserDao {

	private static Connection conn=DataBase.getConnection();
	
	/**
	 * 获取用户		
	 * @param id 用户ID
	 * @return 用户
	 */
	public static User getUser(int id)
	{	
		User user=new User();
		try{
			String sql="select * from users where id = ?";
			PreparedStatement statement=conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result=statement.executeQuery();
			if(result.next())
			{
				user.setId(result.getInt("id"));
				user.setName(result.getString("name"));
				user.setSex(result.getString("sex"));
				user.setAge(result.getInt("age"));
				if(result.getString("address")!=null)
					user.setAddress(result.getString("address"));
				if(result.getString("introduction")!=null)
					user.setIntroduction(result.getString("introduction"));
				if(result.getString("ip")!=null)
					user.setIp(result.getString("ip"));
				if(result.getString("birthday")!=null)
					user.setBirthday(result.getDate("birthday"));
				if(result.getString("company")!=null)
					user.setCompany(result.getString("company"));
				if(result.getString("constellation")!=null)
					user.setConstellation(result.getString("constellation"));
				if(result.getString("email")!=null)
				user.setEmail(result.getString("email"));
				if(result.getString("occupation")!=null)
					user.setOccupation(result.getString("occupation"));
				user.setStatus(result.getBoolean("status"));
				try {
					ImageIcon img=(ImageIcon) new ObjectInputStream(result.getBinaryStream("portrait")).readObject();
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					new ObjectOutputStream(baos).writeObject(img);
					user.setPortrait(baos.toByteArray());
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				}
				if(result.getString("school")!=null)
				user.setSchool(result.getString("school"));
				return user;
			}
			return null;
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/**
	 * 获取用户列表		
	 * @return 用户树
	 */
	public static DefaultTableModel getUserTree(){
		DefaultTableModel model=new DefaultTableModel(
					null,
					new String[] {
						"是否在线", "ID","用户名","IP"
					}
				) {
					private static final long serialVersionUID = -4744079440872850349L;

					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
		String sql="select * from users";
		try {
			Statement statement = conn.createStatement();
			ResultSet res=statement.executeQuery(sql);
			while(res.next()){
				model.addRow(new String[]{res.getBoolean("status")?"*":" ",""+res.getInt("id"),res.getString("name"),res.getString("ip")});
			}
			return model;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	

	/**
	 * 设置在线状态
	 * @param id 用户ID
	 * @param isOnline 是否在线
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean setStatus(int id,boolean isOnline){
		String sql="update users set status=? where id=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setBoolean(1, isOnline);
			statement.setInt(2, id);
			if(statement.executeUpdate()!=0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/**
	 * 全下线
	 */
	public static void allLogout(){
		String sql="update users set status=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setBoolean(1, false);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 更新IP
	 * @param id 用户ID
	 * @param ip 用户IP地址
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean setIp(int id,String ip){
		String sql="update users set ip=? where id=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, ip);
			statement.setInt(2, id);
			if(statement.executeUpdate()!=0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/**
	 * 添加用户		
	 * @param user 用户
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean addUser(User user){
		try{
			String sqlcheck="select * from users where id = ?";
			PreparedStatement statementck=conn.prepareStatement(sqlcheck);
			statementck.setInt(1, Integer.parseInt(user.getId()));
			ResultSet resck=statementck.executeQuery();
			if(resck.next()){
				return false;
			}
			String sql="insert users VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement=conn.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(user.getId()));
			statement.setBinaryStream(2, new ByteArrayInputStream(user.getPortrait()));
			statement.setString(3, user.getName());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getSex());
			if(user.getBirthday()==null){
				statement.setDate(6, null);
			}else{
				statement.setDate(6, new Date(user.getBirthday().getTime()));
			}
			statement.setString(7, user.getEmail());
			if(user.getAge()==null){
				statement.setInt(8, 0);
			}else{
				statement.setInt(8, Integer.parseInt(user.getAge()));
			}
			statement.setString(9, user.getConstellation());
			statement.setString(10, user.getOccupation());
			statement.setString(11, user.getCompany());
			statement.setString(12, user.getSchool());
			statement.setString(13, user.getAddress());
			statement.setString(14, user.getIntroduction());
			statement.setString(15, user.getIp());
			statement.setBoolean(16, false);
			statement.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/**
	 * 修改用户		
	 * @param user 用户
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean editUser(User user){
		try{
			String sql="update users set portrait=?,name=?,sex=?,birthday=?,email=?,constellation=?,occupation=?,age=?,company=?,school=?,address=?,introduction=? where id=?";
			PreparedStatement statement=conn.prepareStatement(sql);
			statement.setBinaryStream(1, new ByteArrayInputStream(user.getPortrait()));
			statement.setString(2, user.getName());
			statement.setString(3, user.getSex());
			if(user.getBirthday()==null){
				statement.setDate(4, null);
			}else{
				statement.setDate(4, new Date(user.getBirthday().getTime()));
			}
			statement.setString(5, user.getEmail());
			statement.setString(6, user.getConstellation());
			statement.setString(7, user.getOccupation());
			if(user.getAge()==null){
				statement.setInt(8, 0);
			}else{
				statement.setInt(8, Integer.parseInt(user.getAge()));
			}
			statement.setString(9, user.getCompany());
			statement.setString(10, user.getSchool());
			statement.setString(11, user.getAddress());
			statement.setString(12, user.getIntroduction());
			statement.setInt(13, Integer.parseInt(user.getId()));
			if(statement.executeUpdate()==0){
				return false;
			}else{
				return true;
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 修改用户密码		
	 * @param id 用户ID
	 * @param old 用户旧密码
	 * @param newp 用户新密码
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean editPasw(int id,String old,String newp){
		try{
			String sql="update users set password=? where id=? and password=?";
			PreparedStatement statement=conn.prepareStatement(sql);
			statement.setString(1, newp);
			statement.setString(3, old);
			statement.setInt(2, id);
			if(statement.executeUpdate()==0){
				return false;
			}else{
				return true;
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 用户登陆
	 * @param id 用户ID
	 * @param password 用户密码
	 * @return true 登陆成功，false 登陆失败
	 */
	public static boolean login(int id,String password)
	{	
		try{
			String sql="select * from users where id = ? and password = ?";
			PreparedStatement statement=conn.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, password);
			ResultSet result=statement.executeQuery();
			while(result.next()){
				return true;
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return false;
	}
	
}

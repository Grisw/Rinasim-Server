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
 * �û���������
 * @date 2015��3��15�� ����8:42:55
 * @since v1.0
 */
public class UserDao {

	private static Connection conn=DataBase.getConnection();
	
	/**
	 * ��ȡ�û�		
	 * @param id �û�ID
	 * @return �û�
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
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
				if(result.getString("school")!=null)
				user.setSchool(result.getString("school"));
				return user;
			}
			return null;
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/**
	 * ��ȡ�û��б�		
	 * @return �û���
	 */
	public static DefaultTableModel getUserTree(){
		DefaultTableModel model=new DefaultTableModel(
					null,
					new String[] {
						"�Ƿ�����", "ID","�û���","IP"
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
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	

	/**
	 * ��������״̬
	 * @param id �û�ID
	 * @param isOnline �Ƿ�����
	 * @return true �޸ĳɹ���false �޸�ʧ��
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
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/**
	 * ȫ����
	 */
	public static void allLogout(){
		String sql="update users set status=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setBoolean(1, false);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * ����IP
	 * @param id �û�ID
	 * @param ip �û�IP��ַ
	 * @return true �޸ĳɹ���false �޸�ʧ��
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
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/**
	 * ����û�		
	 * @param user �û�
	 * @return true �޸ĳɹ���false �޸�ʧ��
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
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/**
	 * �޸��û�		
	 * @param user �û�
	 * @return true �޸ĳɹ���false �޸�ʧ��
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
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * �޸��û�����		
	 * @param id �û�ID
	 * @param old �û�������
	 * @param newp �û�������
	 * @return true �޸ĳɹ���false �޸�ʧ��
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
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * �û���½
	 * @param id �û�ID
	 * @param password �û�����
	 * @return true ��½�ɹ���false ��½ʧ��
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
			JOptionPane.showMessageDialog(null, "��������\n"+e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return false;
	}
	
}

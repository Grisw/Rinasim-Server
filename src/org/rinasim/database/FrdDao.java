package org.rinasim.database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.rinasim.object.FriendPanel;
import org.rinasim.object.Message;
import org.rinasim.object.User;

/**
 * 联系人数据连接
 * @author 刘旭涛
 * @date 2015年3月15日 下午8:42:43
 * @since v1.0
 */
public class FrdDao {

	private static Connection conn=DataBase.getConnection();
	
	/**
	 * 获取联系人表		
	 * @param id 用户ID
	 * @return 联系人表模型
	 */
	public static List<FriendPanel> getFrdPanel(int id){
		List<FriendPanel> list=new ArrayList<FriendPanel>();
		String sql="select * from [dbo].["+id+"]";
		try {
			Statement statement = conn.createStatement();
			ResultSet res=statement.executeQuery(sql);
			while(res.next()){
				ByteArrayInputStream bais=new ByteArrayInputStream(UserDao.getUser(res.getInt("id")).getPortrait());
				try {
					ImageIcon img = (ImageIcon) new ObjectInputStream(bais).readObject();
					list.add(new FriendPanel(img, res.getString("note"), res.getInt("id"), UserDao.getUser(res.getInt("id")).isOnline(), res.getBoolean("hasmsg")));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		}
		return list;
	}
	
	/**
	 * 获取联系人表(Android)		
	 * @param id 用户ID
	 * @return 联系人表
	 */
	public static List<Map<String, Object>> getFrdList(int id){
		List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		String sql="select * from [dbo].["+id+"]";
		try {
			Statement statement = conn.createStatement();
			ResultSet res=statement.executeQuery(sql);
			while(res.next()){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("status",UserDao.getUser(res.getInt("id")).isOnline()+"");
				ByteArrayInputStream bais=new ByteArrayInputStream(UserDao.getUser(res.getInt("id")).getPortrait());
				try {
					ImageIcon img = (ImageIcon) new ObjectInputStream(bais).readObject();
					BufferedImage bi = new BufferedImage(img.getImage().getWidth(null), img.getImage().getHeight(null), BufferedImage.TYPE_INT_RGB);
					bi.createGraphics().drawImage(img.getImage(), 0, 0, null);
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					ImageIO.write(bi, "png", baos);
					map.put("portrait", baos.toByteArray());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				}
				map.put("name", res.getString("note"));
				map.put("id", res.getInt("id")+"");
				map.put("hasmsg", res.getBoolean("hasmsg"));
				list.add(map);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 添加联系人表		
	 * @param id 用户ID
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean addTable(int id){
		String sql="CREATE TABLE [dbo].["+id+"]([id] [int] NOT NULL,[record] [varbinary](max) NULL,[hasmsg] [bit] NOT NULL,[note] [nvarchar](18) NOT NULL) ON [PRIMARY]";
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加联系人		
	 * @param id 用户ID
	 * @param frdid 联系人ID
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean appendFrd(int id,int frdid){
		String sql="insert [dbo].["+id+"] values (?,?,?,?)";
		try {
			String sqlcheck="select * from [dbo].["+id+"] where id = ?";
			PreparedStatement statementck=conn.prepareStatement(sqlcheck);
			statementck.setInt(1, frdid);
			ResultSet resck=statementck.executeQuery();
			if(resck.next()||frdid==id){
				return false;
			}
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, frdid);
			statement.setBinaryStream(2, null);
			statement.setBoolean(3, false);
			statement.setString(4, UserDao.getUser(frdid).getName());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除联系人		
	 * @param id 用户ID
	 * @param frdid 联系人ID
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean deleteFrd(int id,int frdid){
		String sql="delete from [dbo].["+id+"] where id = ?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, frdid);
			if(statement.executeUpdate()==0){
				return false;
			}else{
				return true;				
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取联系人		
	 * @param id 用户ID
	 * @param frdId 联系人ID
	 * @return 用户对象
	 */
	public static User getFrd(int id,int frdId){
		String sql="select * from [dbo].["+id+"] where id=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, frdId);
			ResultSet res=statement.executeQuery();
			if(res.next()){
				return UserDao.getUser(res.getInt(1));
			}
			return null;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取聊天记录
	 * @param id 请求发起的ID
	 * @param frdId 要求的联系人ID
	 * @return 信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Message> getOfflineMsg(int id,int frdId){
		String sql="select * from [dbo].["+id+"] where id=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, frdId);
			ResultSet res=statement.executeQuery();
			if(res.next()){
				Blob record=res.getBlob("record");
				if(record!=null){
					ObjectInputStream reader=new ObjectInputStream(record.getBinaryStream());
					List<Message> list=(List<Message>) reader.readObject();
					reader.close();
					return list;
				}
			}
			return null;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/**
	 * 设置聊天记录
	 * @param id 请求发起Id
	 * @param frdId 要求的联系人ID
	 * @param list 会话列表
	 * @return true 修改成功，false 修改失败
	 */
	public static boolean setOfflineMsg(int id,int frdId,List<Message> list){
		String sql="update [dbo].["+id+"] set record=? where id=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ByteArrayOutputStream bs=new ByteArrayOutputStream();
			ObjectOutputStream writer=new ObjectOutputStream(bs);
			writer.writeObject(list);
			statement.setBinaryStream(1, new ByteArrayInputStream(bs.toByteArray()));
			statement.setInt(2, frdId);
			if(statement.executeUpdate()!=0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/**
	 * 获取新消息状态
	 * @param id 
	 * @param frdId
	 * @return
	 */
	public static boolean hasMessage(int id, int frdId){
		String sql="select * from [dbo].["+id+"] where id=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, frdId);
			ResultSet res=statement.executeQuery();
			if(res.next()){
				boolean hasMsg=res.getBoolean("hasmsg");
				return hasMsg;
			}
			return false;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 设置是否有新消息
	 * @param id
	 * @param frdId
	 * @return
	 */
	public static boolean setHasMessage(int id, int frdId, boolean hasMsg){
		String sql="update [dbo].["+id+"] set hasmsg=? where id=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setBoolean(1, hasMsg);
			statement.setInt(2, frdId);
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
	 * 获取所有联系人状态
	 * @param id 发起请求的ID
	 * @return
	 */
	public static Map<Integer, Boolean> getFrdStatus(int id){
		String sql="select * from [dbo].["+id+"]";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet res=statement.executeQuery();
			Map<Integer, Boolean> map=new HashMap<Integer, Boolean>();
			if(res.next()){
				boolean status=UserDao.getUser(res.getInt("id")).isOnline();
				map.put(res.getInt("id"), status);
			}
			return map;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 设置备注
	 * @param id
	 * @param frdId
	 * @param note
	 * @return
	 */
	public static boolean setFrdNote(int id,int frdId,String note){
		String sql="update [dbo].["+id+"] set note=? where id=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, note);
			statement.setInt(2, frdId);
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
}

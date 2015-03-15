package org.rinasim.object;

import java.io.Serializable;

/**
 * 聊天信息
 * @author MissingNo.
 * @date 2015年2月11日 下午8:06:23
 * @version v3.0
 */
public class Message implements Serializable{

	private static final long serialVersionUID = -8689340103713911133L;

	/**
	 * 接收的消息类型
	 */
	public static final int FROM=1;
	
	/**
	 * 发送的消息类型
	 */
	public static final int TO=2;

	private String msg;
	private String time;
	private int type;

	/**
	 * 创建消息
	 * @param msg 消息内容
	 * @param time 消息发送时间
	 * @param type 消息类型，PopMessage.FROM 或 PopMessage.TO
	 */
	public Message(String msg, String time,	int type){
		this.msg=msg;
		this.time=time;
		this.type=type;
	}
	
	/**
	 * 获取消息内容
	 * @return 消息内容
	 */
	public String getMessage(){
		return msg;
	}
	
	/**
	 * 获取时间
	 * @return 消息发送的时间
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * 获取消息类型
	 * @return 消息类型，PopMessage.FROM 或 PopMessage.TO
	 */
	public int getType(){
		return type;
	}
}

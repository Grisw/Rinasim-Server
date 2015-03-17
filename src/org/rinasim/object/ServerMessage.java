package org.rinasim.object;

import java.io.Serializable;

/**
 * 服务器处理的信息
 * @date 2015年3月15日 下午8:36:05
 * @since v1.0
 */
public class ServerMessage implements Serializable{

	private static final long serialVersionUID = -7150142407183690653L;
	
	private String time;
	private String msg;
	
	/**
	 * 构造信息
	 * @param time 储存的时间
	 * @param msg 储存的信息
	 */
	public ServerMessage(String time, String msg){
		this.time=time;
		this.msg=msg;
	}
	
	/**
	 * 获取时间
	 * @return 时间
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * 获取信息
	 * @return 信息
	 */
	public String getMessage(){
		return msg;
	}
}

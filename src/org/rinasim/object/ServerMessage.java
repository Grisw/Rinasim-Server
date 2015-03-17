package org.rinasim.object;

import java.io.Serializable;

/**
 * �������������Ϣ
 * @date 2015��3��15�� ����8:36:05
 * @since v1.0
 */
public class ServerMessage implements Serializable{

	private static final long serialVersionUID = -7150142407183690653L;
	
	private String time;
	private String msg;
	
	/**
	 * ������Ϣ
	 * @param time �����ʱ��
	 * @param msg �������Ϣ
	 */
	public ServerMessage(String time, String msg){
		this.time=time;
		this.msg=msg;
	}
	
	/**
	 * ��ȡʱ��
	 * @return ʱ��
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * ��ȡ��Ϣ
	 * @return ��Ϣ
	 */
	public String getMessage(){
		return msg;
	}
}

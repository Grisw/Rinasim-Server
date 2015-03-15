package org.rinasim.object;

import java.io.Serializable;

/**
 * ������Ϣ
 * @author ������
 * @date 2015��3��15�� ����9:11:36
 * @since v1.0
 */
public class Message implements Serializable{

	private static final long serialVersionUID = -8689340103713911133L;

	/**
	 * ���յ���Ϣ����
	 */
	public static final int FROM=1;
	
	/**
	 * ���͵���Ϣ����
	 */
	public static final int TO=2;

	private String msg;
	private String time;
	private int type;

	/**
	 * ������Ϣ
	 * @param msg ��Ϣ����
	 * @param time ��Ϣ����ʱ��
	 * @param type ��Ϣ���ͣ�PopMessage.FROM �� PopMessage.TO
	 */
	public Message(String msg, String time,	int type){
		this.msg=msg;
		this.time=time;
		this.type=type;
	}
	
	/**
	 * ��ȡ��Ϣ����
	 * @return ��Ϣ����
	 */
	public String getMessage(){
		return msg;
	}
	
	/**
	 * ��ȡʱ��
	 * @return ��Ϣ���͵�ʱ��
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * ��ȡ��Ϣ����
	 * @return ��Ϣ���ͣ�PopMessage.FROM �� PopMessage.TO
	 */
	public int getType(){
		return type;
	}
}

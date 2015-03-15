package org.rinasim.util;

/**
 * ��Ϣ��ʶ��
 * @author ������
 * @date 2015��3��15�� ����8:33:14
 * @since v1.0
 */
public final class Identifier {

	/**
	 * ���������͵���Ϣ
	 * ��ʽ�� SERVER->String time->String msg
	 */
	public static final int SERVER=1;
	
	/**
	 * ��ͨ��Ϣ
	 * ��ʽ�� MSG->int to->Message msg
	 */
	public static final int MSG=2;
	
	/**
	 * ��½��Ϣ
	 * ��ʽ��LOGIN->int id->String password
	 */
	public static final int LOGIN=3;
	
	/**
	 * ��ȡ�û���Ϣ
	 * ��ʽ��GET_USER_INFO->int id
	 */
	public static final int GET_USER_INFO=4;
	
	/**
	 * ע����Ϣ
	 * ��ʽ��REGISTER->User user
	 */
	public static final int REGISTER=5;
	
	/**
	 * �޸��û���Ϣ
	 * ��ʽ��EDIT_USER_INFO->User user
	 */
	public static final int EDIT_USER_INFO=6;
	
	/**
	 * �޸��û�������Ϣ
	 * ��ʽ��EDIT_PASSWORD->String oldPasw->String newPasw
	 */
	public static final int EDIT_PASSWORD=7;
	
	/**
	 * ��ȡ�����б���Ϣ
	 * ��ʽ��GET_FRIEND_TREE
	 */
	public static final int GET_FRIEND_TREE=8;

	/**
	 * �����ϵ����Ϣ
	 * ��ʽ��ADD_FRIEND->int frdId
	 */
	public static final int ADD_FRIEND=10;
	
	/**
	 * ɾ����ϵ����Ϣ
	 * ��ʽ��DELETE_FRIEND->int frdId
	 */
	public static final int DELETE_FRIEND=11;
	
	/**
	 * ���͸���
	 * ��ʽ��PUSH_UPDATE->int length->String time->String version
	 */
	public static final int PUSH_UPDATE=12;
	
	/**
	 * �������
	 * ��ʽ��UPDATE->int length->String name->byte[] data
	 */
	public static final int UPDATE=13;

	/**
	 * ������Ϣ
	 * ��ʽ��OFFLINE_MESSAGE->int frdId
	 */
	public static final int OFFLINE_MESSAGE=15;
	
	/**
	 * ��ϵ��״̬
	 * ��ʽ��GET_FRIEND_STATUS
	 */
	public static final int GET_FRIEND_STATUS=16;
	
	/**
	 * ������ϵ�˱�ע
	 * ��ʽ��SET_FRIEND_NOTE->int frdId->String note
	 */
	public static final int SET_FRIEND_NOTE=17;
	
	/**
	 * �Ѷ���Ϣ
	 * ��ʽ��READ_MESSAGE->int frdId
	 */
	public static final int READ_MESSAGE=18;

}

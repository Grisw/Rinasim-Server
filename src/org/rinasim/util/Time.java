package org.rinasim.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ʱ��
 * @date 2015��3��15�� ����8:34:32
 * @since v1.0
 */
public class Time {

	/**
	 * ��ȡʱ�䵽��
	 * @return ��ǰʱ��
	 */
	public static String getDateTime(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		String strDate=format.format(date);
		return strDate;
	}

	/**
	 * ��ȡʱ�䵽����
	 * @return ��ǰʱ��
	 */
	public static String getDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=new Date();
		String strDate=format.format(date);
		return strDate;
	}
	
}

package org.rinasim.main;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 启动程序的入口
 * @since v1.0
 * @author 刘旭涛
 * @date 2015年3月14日 下午1:39:56
 */
public class Main {

	/**
	 * 主处理
	 * @author 刘旭涛
	 * @date 2015年3月14日 下午1:42:41
	 * @since v1.0
	 */
	public static void main(String[] args) {
		
		//设定LookAndFeel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Label.font",new Font("微软雅黑", java.awt.Font.PLAIN, 12));
			UIManager.put("Button.font",new Font("微软雅黑", java.awt.Font.PLAIN, 12));
			UIManager.put("TextField.font",new Font("微软雅黑", java.awt.Font.PLAIN, 12));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}

}

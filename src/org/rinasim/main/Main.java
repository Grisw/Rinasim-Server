package org.rinasim.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * ������������
 * @since v1.0
 * @author ������
 * @date 2015��3��14�� ����1:39:56
 */
public class Main {

	/**
	 * ������
	 * @author ������
	 * @date 2015��3��14�� ����1:42:41
	 * @since v1.0
	 */
	public static void main(String[] args) {
		
		//�趨LookAndFeel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		System.out.println("tvgf");
	}

}

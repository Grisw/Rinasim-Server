package org.rinasim.main;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.rinasim.frame.Login;


/**
 * ������������
 * @since v1.0
 * @author ������
 * @date 2015��3��14�� ����1:39:56
 */
public class Main {

	private static final int TEXT_SIZE=14;
	
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
			UIManager.put("Label.font",new Font("΢���ź�", java.awt.Font.PLAIN, TEXT_SIZE));
			UIManager.put("Button.font",new Font("΢���ź�", java.awt.Font.PLAIN, TEXT_SIZE));
			UIManager.put("TextField.font",new Font("΢���ź�", java.awt.Font.PLAIN, TEXT_SIZE));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//������½����
		new Login().setVisible(true);
		
	}

}

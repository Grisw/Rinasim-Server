package org.rinasim.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

/**
 * ������������½����
 * @author ������
 * @since v1.0
 * @date 2015��3��15�� ����9:36:37
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 8620485868120123682L;
	private JPanel pnl;
	private JPasswordField passwordField;
	private JButton btn;
	private JLabel lbl;

	public Login() {
		//����������
		this.setResizable(false);
		this.setTitle("RinasimServer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 428, 242);
		//���
		pnl = new JPanel();
		pnl.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(pnl);
		pnl.setLayout(null);
		//�����ǩ
		lbl = new JLabel("\u5BC6\u7801\uFF1A");
		lbl.setBounds(64, 68, 45, 18);
		//���������
		passwordField = new JPasswordField();
		passwordField.setBounds(123, 65, 196, 24);
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�����...
			}
		});
		//��½��ť
		btn = new JButton("\u767B\u9646");
		btn.setBounds(154, 147, 113, 27);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��½...
			}
		});
		
		pnl.add(lbl);
		pnl.add(passwordField);
		pnl.add(btn);
	}
}

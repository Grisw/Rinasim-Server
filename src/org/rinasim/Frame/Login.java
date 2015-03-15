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
 * 创建服务器登陆界面
 * @author 李孟珂
 * @since v1.0
 * @date 2015年3月15日 上午9:36:37
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 8620485868120123682L;
	private JPanel pnl;
	private JPasswordField passwordField;
	private JButton btn;
	private JLabel lbl;

	public Login() {
		//设立窗体框架
		this.setResizable(false);
		this.setTitle("RinasimServer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 428, 242);
		//面板
		pnl = new JPanel();
		pnl.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(pnl);
		pnl.setLayout(null);
		//密码标签
		lbl = new JLabel("\u5BC6\u7801\uFF1A");
		lbl.setBounds(64, 68, 45, 18);
		//密码输入框
		passwordField = new JPasswordField();
		passwordField.setBounds(123, 65, 196, 24);
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//密码框...
			}
		});
		//登陆按钮
		btn = new JButton("\u767B\u9646");
		btn.setBounds(154, 147, 113, 27);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//登陆...
			}
		});
		
		pnl.add(lbl);
		pnl.add(passwordField);
		pnl.add(btn);
	}
}

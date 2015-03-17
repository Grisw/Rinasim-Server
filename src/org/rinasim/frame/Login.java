package org.rinasim.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import org.rinasim.main.Main;
import org.rinasim.util.FileOperator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * 登录服务器
 * @author 刘旭涛
 * @date 2015年3月16日 下午7:40:27
 * @since v1.0
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 7286839629403414832L;
	
	private JPanel contentPane;
	private JPasswordField password;

	/**
	 * 初始化窗体
	 */
	public Login() {
		
		//初始化
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 397, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		setTitle("\u767B\u5F55\u670D\u52A1\u5668");
		
		//添加组件
		JLabel label = new JLabel("\u5BC6\u7801\uFF1A");
		label.setBounds(30, 55, 45, 18);
		contentPane.add(label);
		
		JLabel lblCardinalServerV = new JLabel("Rinasim Server v1.0");
		lblCardinalServerV.setBounds(210, 110, 167, 18);
		contentPane.add(lblCardinalServerV);
		
		password = new JPasswordField();
		password.setBounds(77, 52, 173, 24);
		contentPane.add(password);
		
		JButton login = new JButton("\u767B\u5F55");
		login.setBounds(264, 49, 113, 30);
		contentPane.add(login);
		
		//设置监听
		ActionListener listener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				char[] p=password.getPassword();
				for(int i=0;i<p.length;i++){
					p[i]=(char) (p[i]^Main.PASSWORD_OPERATOR);
				}
				if(FileOperator.getPassword()!=null&&Arrays.equals(FileOperator.getPassword(), p)){
					ServerUI frame=new ServerUI();
					frame.setVisible(true);
					Login.this.dispose();
				}else{
					JOptionPane.showMessageDialog(Login.this, "密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		login.addActionListener(listener);
		password.addActionListener(listener);
	}
}

package org.rinasim.frame;

import javax.swing.JDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import org.rinasim.main.Main;
import org.rinasim.util.FileOperator;

/**
 * 修改密码窗体
 * @author 刘旭涛
 * @date 2015年3月15日 下午8:26:23
 * @since v1.0
 */
public class EditPassword extends JDialog {

	private static final long serialVersionUID = 7352200887723970000L;
	
	private JPasswordField oldp;
	private JPasswordField newp;
	private JPasswordField repeat;

	/**
	 * 创建窗体
	 */
	public EditPassword() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//初始化
		setModal(true);
		setTitle("\u4FEE\u6539\u5BC6\u7801");
		setResizable(false);
		setBounds(100, 100, 370, 172);
		getContentPane().setLayout(null);
		
		//配置面板
		JLabel label = new JLabel("\u65E7\u5BC6\u7801\uFF1A");
		label.setBounds(26, 20, 60, 18);
		getContentPane().add(label);
		
		oldp = new JPasswordField();
		oldp.setBounds(93, 17, 129, 24);
		getContentPane().add(oldp);
		
		JLabel label_1 = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		label_1.setBounds(26, 57, 60, 18);
		getContentPane().add(label_1);
		
		newp = new JPasswordField();
		newp.setBounds(93, 54, 129, 24);
		getContentPane().add(newp);
		
		JLabel label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		label_2.setBounds(14, 96, 77, 18);
		getContentPane().add(label_2);
		
		repeat = new JPasswordField();
		repeat.setBounds(93, 93, 129, 24);
		getContentPane().add(repeat);
		repeat.setColumns(10);
		
		JButton ok = new JButton("\u786E\u8BA4");
		ok.setBounds(236, 53, 113, 27);
		getContentPane().add(ok);

		//设置监听
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(newp.getPassword().length>=5){
					if(String.valueOf(newp.getPassword()).equals(String.valueOf(repeat.getPassword()))){
						char[] p=oldp.getPassword();
						for(int i=0;i<p.length;i++){
							p[i]=(char) (p[i]^Main.PASSWORD_OPERATOR);
						}
						if(FileOperator.getPassword()!=null&&Arrays.equals(p, FileOperator.getPassword())){
							p=newp.getPassword();
							for(int i=0;i<p.length;i++){
								p[i]=(char) (p[i]^Main.PASSWORD_OPERATOR);
							}
							FileOperator.writePassword(p);
							JOptionPane.showMessageDialog(EditPassword.this, "修改成功！", "信息", JOptionPane.INFORMATION_MESSAGE);
							EditPassword.this.dispose();
						}else{
							JOptionPane.showMessageDialog(EditPassword.this, "旧密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(EditPassword.this, "两次密码输入不一致！", "错误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(EditPassword.this, "密码长度不小于5个字符！", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}

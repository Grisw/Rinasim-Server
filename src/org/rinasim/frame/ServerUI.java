/**
 * 
 */
package org.rinasim.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.rinasim.database.UserDao;
import org.rinasim.network.Server;
import org.rinasim.object.ServerMessage;
import org.rinasim.util.FileOperator;
import org.rinasim.util.Time;

/**
 * 服务器主窗体
 * @author 刘旭涛
 * @date 2015年3月15日 下午7:41:45
 * @since v1.0
 */
public class ServerUI extends JFrame {

	private static final long serialVersionUID = 67013013114928312L;

	/**
	 * 用于显示信息的文本区
	 */
	public static JTextArea serverInfo;
	
	private Server server;
	
	private JPanel contentPane;
	public JTable table;
	private JTextField serverPort;
	private JLabel count;

	/**
	 * 创建窗体
	 */
	public ServerUI() {
		
		//初始化
		setTitle("\u670D\u52A1\u5668");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1153, 507);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//配置面板
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u670D\u52A1\u5668\u63A7\u5236", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u66F4\u65B0\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u7528\u6237\u63A7\u5236", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "\u670D\u52A1\u5668\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
							.addGap(135))
						.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(7))
		);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton broadCast = new JButton("\u53D1\u5E03\u516C\u544A");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
						.addComponent(broadCast, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(broadCast, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JTextArea broadText = new JTextArea();
		broadText.setEditable(false);
		broadText.setLineWrap(true);
		broadText.setWrapStyleWord(true);
		scrollPane_2.setViewportView(broadText);
		
		JLabel label = new JLabel("\u516C\u544A");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_2.setColumnHeaderView(label);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(UserDao.getUserTree());
		scrollPane.setViewportView(table);
		panel_4.setLayout(gl_panel_4);
		
		JLabel serverIp = new JLabel("\u670D\u52A1\u5668IP\u5730\u5740\uFF1A");
		
		JLabel label_2 = new JLabel("\u670D\u52A1\u5668\u7AEF\u53E3\uFF1A");
		
		serverPort = new JTextField();
		serverPort.setColumns(10);
		
		JLabel lblv = new JLabel("\u6570\u636E\u5E93\u7248\u672C\uFF1ARinasim v1.0");
		
		JLabel lblcardinalV = new JLabel("\u670D\u52A1\u5668\u7248\u672C\uFF1ARinasim v1.0");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		count = new JLabel("\u63A5\u5165\u6570\uFF1A0");
		
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addComponent(lblcardinalV, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(serverIp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_panel_5.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addGap(1)
							.addComponent(serverPort, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
							.addGap(0))
						.addComponent(lblv, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addComponent(count, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addComponent(serverIp, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(serverPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblcardinalV, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblv, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(count)
					.addGap(18)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		serverInfo = new JTextArea();
		serverInfo.setEditable(false);
		serverInfo.setLineWrap(true);
		serverInfo.setWrapStyleWord(true);
		scrollPane_1.setViewportView(serverInfo);
		
		JLabel label_3 = new JLabel("\u8FDE\u63A5\u4FE1\u606F");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(label_3);
		panel_5.setLayout(gl_panel_5);
		
		JLabel pcVersion = new JLabel("\u7248\u672C\uFF1A");
		
		JButton pcUpdate = new JButton("\u53D1\u5E03\u66F4\u65B0");
		pcUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser=new JFileChooser(".");
				chooser.setFileFilter(new FileNameExtensionFilter("Jar文件", ".jar"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				int returns=chooser.showOpenDialog(ServerUI.this);
				if(returns==JFileChooser.APPROVE_OPTION){
					pcVersion.setText("\u7248\u672C\uFF1A"+chooser.getSelectedFile().getAbsolutePath().substring(chooser.getSelectedFile().getAbsolutePath().lastIndexOf("\\"), chooser.getSelectedFile().getAbsolutePath().lastIndexOf(".")));
					FileOperator.writeUpdate(new ServerMessage(Time.getDateTime(), chooser.getSelectedFile().getAbsolutePath()));
					server.pushUpdate(chooser.getSelectedFile().getAbsolutePath().substring(chooser.getSelectedFile().getAbsolutePath().lastIndexOf("\\"), chooser.getSelectedFile().getAbsolutePath().lastIndexOf(".")));
					serverInfo.append("("+Time.getDateTime()+")发布PC更新！版本："+chooser.getSelectedFile().getAbsolutePath().substring(chooser.getSelectedFile().getAbsolutePath().lastIndexOf("\\"), chooser.getSelectedFile().getAbsolutePath().lastIndexOf("."))+"\n");
					serverInfo.setCaretPosition(serverInfo.getText().length());
				}
			}
		});
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(pcVersion, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(pcUpdate, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(pcVersion)
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(pcUpdate)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JButton exit = new JButton("\u9000\u51FA\u670D\u52A1\u5668");
		
		JToggleButton startAndClose = new JToggleButton("\u542F\u52A8\u670D\u52A1\u5668");
		
		JButton editPassword = new JButton("\u4FEE\u6539\u5BC6\u7801");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(startAndClose, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(exit, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(editPassword, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(startAndClose, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(exit, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(editPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		//初始化信息
		try {
			serverIp.setText("\u670D\u52A1\u5668IP\u5730\u5740\uFF1A"+InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(ServerUI.this, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		try {
			String pcv=FileOperator.getUpdatePath().getMessage();
			String pct=FileOperator.getUpdatePath().getTime();
			if(new File(pcv).isFile()){
				pcVersion.setText("\u7248\u672C\uFF1A("+pct+")"+pcv.substring(pcv.lastIndexOf("\\"), pcv.lastIndexOf(".")));
			}else{
				pcVersion.setText("\u7248\u672C\uFF1A没有更新");
			}
		} catch (Exception e) {
			pcVersion.setText("\u7248\u672C\uFF1A没有更新");
			e.printStackTrace();
		}

		try {
			serverPort.setText(""+FileOperator.getPort());
		} catch (IOException e) {
			serverPort.setText("1024");
			e.printStackTrace();
		}
		
		table.setModel(UserDao.getUserTree());
		
		try {
			String time="("+FileOperator.getBroadCast().getTime()+")";
			String content=FileOperator.getBroadCast().getMessage();
			broadText.setText(time+content);
		} catch (IOException e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "发生错误！\n"+e2.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		}
		
		//设置监听
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserDao.allLogout();
				System.exit(0);
			}
		});
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				UserDao.allLogout();
				System.exit(0);
			}
			
			@Override
			public void windowOpened(WindowEvent e) {
				Timer timer=new Timer(1000, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						table.setModel(UserDao.getUserTree());
						count.setText("\u63A5\u5165\u6570\uFF1A"+Server.clients.size());
					}
				});
				timer.start();
			}
		});
		serverPort.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				String regex="0123456789"+(char)8;	
				if(regex.indexOf(e.getKeyChar())<0){
					e.consume();	
				}
			}
		});
		startAndClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(startAndClose.isSelected()){
					if(serverPort.getText().isEmpty()||Integer.parseInt(serverPort.getText())<1024||Integer.parseInt(serverPort.getText())>5000){
						JOptionPane.showMessageDialog(ServerUI.this, "端口范围在1024~5000之间！", "错误", JOptionPane.ERROR_MESSAGE);
						startAndClose.setSelected(false);
						return;
					}
					FileOperator.writePort(Integer.parseInt(serverPort.getText()));
					serverPort.setEditable(false);
					try {
						server=new Server(Integer.parseInt(serverPort.getText()));
						startAndClose.setText("关闭服务器");
					} catch (BindException e) {
						serverInfo.append("("+Time.getDateTime()+")端口已被占用！\n");
						serverInfo.setCaretPosition(serverInfo.getText().length());
						startAndClose.setSelected(false);
						e.printStackTrace();
					} catch (IOException e) {
						serverInfo.append("("+Time.getDateTime()+")创建连接失败！\n");
						serverInfo.setCaretPosition(serverInfo.getText().length());
						JOptionPane.showMessageDialog(ServerUI.this, "发生错误！\n"+e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						startAndClose.setSelected(false);
					}
				}else{
					startAndClose.setText("开启服务器");
					serverPort.setEditable(true);
					UserDao.allLogout();
					server.close();
				}
			}
		});
		editPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditPassword().setVisible(true);
			}
		});
		broadCast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(server!=null){
					String msg=JOptionPane.showInputDialog(ServerUI.this, "向全体用户：", "公告", JOptionPane.INFORMATION_MESSAGE);
					if(!msg.isEmpty()){
						server.broadCast(msg);
						broadText.setText("("+Time.getDate()+")"+msg);
						FileOperator.writeBroadCast(new ServerMessage(Time.getDate(), msg));
					}
				}else{
					JOptionPane.showMessageDialog(ServerUI.this, "请先建立连接！", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}

package org.rinasim.object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;

import javax.swing.JPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JMenuItem;

public class FriendPanel extends JPanel{

	private static final long serialVersionUID = -6995291838537322713L;

	private int id;
	private String name;
	private boolean hasMsg;
	
	private ImageIcon portrait;
	private JLabel portraitLabel;
	private JLabel nameLabel;
	
	private JPopupMenu popupMenu;
	private JMenuItem frdDetails;
	private JMenuItem setFrdNote;
	private JMenuItem deleteFrd;

	private Timer timer;
	
	/**
	 * ������ͼ
	 */
	public FriendPanel(ImageIcon portrait, String name, int id, boolean isOnline, boolean hasMsg) {
		
		//��ʼ��
		setBorder(new LineBorder(UIManager.getColor("Button.background"), 1, true));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		
		setIsOnline(isOnline);
		
		this.portrait=portrait;
		this.name=name;
		this.id=id;
		this.hasMsg=hasMsg;
		
		//�Ҽ��˵�
		popupMenu = new JPopupMenu();
		
		frdDetails = new JMenuItem("\u8BE6\u7EC6\u4FE1\u606F");
		popupMenu.add(frdDetails);
		
		setFrdNote = new JMenuItem("\u7F16\u8F91\u5907\u6CE8");
		popupMenu.add(setFrdNote);
		
		deleteFrd = new JMenuItem("\u5220\u9664");
		popupMenu.add(deleteFrd);
		
		//ͷ���ǩ
		portraitLabel = new JLabel(portrait);
		add(portraitLabel);
		
		//�հ�
		Component rigidArea = Box.createRigidArea(new Dimension(10, 20));
		add(rigidArea);
		
		//�������
		JPanel nameAndId=new JPanel();
		nameAndId.setLayout(new BoxLayout(nameAndId, BoxLayout.Y_AXIS));
		nameAndId.setOpaque(false);
		add(nameAndId);
		
		//������ʾ
		nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("����", Font.PLAIN, 18));
		nameLabel.setOpaque(false);
		nameAndId.add(nameLabel);
		
		//ID��ʾ
		JLabel idLabel = new JLabel(id+"");
		idLabel.setFont(new Font("��Բ", Font.PLAIN, 15));
		idLabel.setOpaque(false);
		nameAndId.add(idLabel);
		
		//���
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);

	}

	/**
	 * ��������״̬
	 * @param isOnline true����
	 */
	public void setIsOnline(boolean isOnline){
		if(isOnline){
			setBackground(Color.WHITE);
		}else{
			setBackground(UIManager.getColor("Button.background"));
		}
	}
	
	/**
	 * �����Ƿ�������Ϣ
	 * @param hasMsg
	 */
	public void setHasMsg(boolean hasMsg){
		if(hasMsg&&!this.hasMsg){
			timer=new Timer(500, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(portraitLabel.getIcon().equals(portrait)){
						BufferedImage image = new BufferedImage(48, 48, BufferedImage.TYPE_INT_RGB);
						Graphics2D g2d = image.createGraphics();
						image = g2d.getDeviceConfiguration().createCompatibleImage(48, 48, Transparency.TRANSLUCENT);
						g2d.dispose();
						g2d = image.createGraphics();
						g2d.setColor(new Color(255,0,0));
						g2d.setStroke(new BasicStroke(1));
						g2d.dispose();
						portraitLabel.setIcon(new ImageIcon(image));
					}else{
						portraitLabel.setIcon(portrait);
					}
				}
			});
			timer.start();
		}else{
			if(timer!=null){
				timer.stop();
			}
			portraitLabel.setIcon(portrait);
		}
		this.hasMsg=hasMsg;
	}
	
	/**
	 * �����Ƿ�������Ϣ(Ĭ��)
	 */
	public void setHasMsg(){
		if(hasMsg){
			timer=new Timer(500, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(portraitLabel.getIcon().equals(portrait)){
						BufferedImage image = new BufferedImage(48, 48, BufferedImage.TYPE_INT_RGB);
						Graphics2D g2d = image.createGraphics();
						image = g2d.getDeviceConfiguration().createCompatibleImage(48, 48, Transparency.TRANSLUCENT);
						g2d.dispose();
						g2d = image.createGraphics();
						g2d.setColor(new Color(255,0,0));
						g2d.setStroke(new BasicStroke(1));
						g2d.dispose();
						portraitLabel.setIcon(new ImageIcon(image));
					}else{
						portraitLabel.setIcon(portrait);
					}
				}
			});
			timer.start();
		}
	}
	
	/**
	 * ��ȡID
	 * @return �û�ID
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * ��ȡ����
	 * @return ����
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * ��ȡͷ��
	 * @return ͷ��
	 */
	public ImageIcon getPortrait(){
		return portrait;
	}
	
	/**
	 * �����¼�����
	 * @param details
	 * @param setNote
	 * @param delete
	 * @param click
	 */
	public void setListeners(ActionListener details,ActionListener setNote, ActionListener delete, MouseAdapter click){
		frdDetails.addActionListener(details);
		setFrdNote.addActionListener(setNote);
		deleteFrd.addActionListener(delete);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				setBorder(new BevelBorder(BevelBorder.LOWERED));
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBorder(new LineBorder(UIManager.getColor("Button.background"), 1, true));
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e){
				if(!e.isPopupTrigger()){
					click.mouseClicked(e);
					setHasMsg(false);
				}
			}
			
			private void showMenu(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	/**
	 * ���ñ�ע����
	 * @param note
	 */
	public void setNote(String note){
		name=note;
		nameLabel.setText(note);
	}
}

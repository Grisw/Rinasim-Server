package org.rinasim.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * 发送邮件
 * @author 刘旭涛
 * @date 2015年3月18日 下午6:33:46
 * @since v1.0
 */
public class SimpleMailSender {
	
    /**
     * 发送邮件的props文件
     */
    private final Properties props = System.getProperties();
    
    /**
     * 邮件服务器登录验证
     */
    private MailAuthenticator authenticator;
 
    /**
     * 邮箱session
     */
    private Session session;
 
    /**
     * 初始化邮件发送器
     */
    public SimpleMailSender() {
    	// 验证
    	authenticator = new MailAuthenticator();
    	// 初始化props
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.host", "smtp." + MailAuthenticator.USERNAME.split("@")[1]);
    	// 创建session
    	session = Session.getInstance(props, authenticator);
    }

    /**
     * 发送邮件
     * 
     * @param recipient
     *                收件人邮箱地址
     * @param subject
     *                邮件主题
     * @param content
     *                邮件内容
     * @throws AddressException
     * @throws MessagingException
     * @throws UnsupportedEncodingException 
     */
    public void send(String recipient, String subject, String content) throws AddressException, MessagingException, UnsupportedEncodingException {
    	// 创建mime类型邮件
    	MimeMessage message = new MimeMessage(session);
    	// 设置发信人
    	message.setFrom(new InternetAddress(MailAuthenticator.USERNAME, MimeUtility.encodeText(MailAuthenticator.NAME)));
    	// 设置收件人
    	message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
    	// 设置主题
    	message.setSubject(subject);
    	// 设置邮件内容
    	message.setContent(content, "text/html;charset=utf-8");
    	// 发送
    	Transport.send(message);
    }
 
    /**
     * 群发邮件
     * 
     * @param recipients
     *                收件人们
     * @param subject
     *                主题
     * @param content
     *                内容
     * @throws AddressException
     * @throws MessagingException
     * @throws UnsupportedEncodingException 
     */
    public void send(List<String> recipients, String subject, String content) throws AddressException, MessagingException, UnsupportedEncodingException {
    	// 创建mime类型邮件
    	MimeMessage message = new MimeMessage(session);
    	// 设置发信人
    	message.setFrom(new InternetAddress(MailAuthenticator.USERNAME, MimeUtility.encodeText(MailAuthenticator.NAME)));
    	// 设置收件人们
    	int num = recipients.size();
    	InternetAddress[] addresses = new InternetAddress[num];
    	for (int i = 0; i < num; i++) {
    		addresses[i] = new InternetAddress(recipients.get(i));
    	}
    	message.setRecipients(RecipientType.TO, addresses);
    	// 设置主题
    	message.setSubject(subject);
    	// 设置邮件内容
    	message.setContent(content, "text/html;charset=utf-8");
    	// 发送
    	Transport.send(message);
    }
 
}

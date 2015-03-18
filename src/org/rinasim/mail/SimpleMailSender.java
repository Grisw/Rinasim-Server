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
 * �����ʼ�
 * @author ������
 * @date 2015��3��18�� ����6:33:46
 * @since v1.0
 */
public class SimpleMailSender {
	
    /**
     * �����ʼ���props�ļ�
     */
    private final Properties props = System.getProperties();
    
    /**
     * �ʼ���������¼��֤
     */
    private MailAuthenticator authenticator;
 
    /**
     * ����session
     */
    private Session session;
 
    /**
     * ��ʼ���ʼ�������
     */
    public SimpleMailSender() {
    	// ��֤
    	authenticator = new MailAuthenticator();
    	// ��ʼ��props
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.host", "smtp." + MailAuthenticator.USERNAME.split("@")[1]);
    	// ����session
    	session = Session.getInstance(props, authenticator);
    }

    /**
     * �����ʼ�
     * 
     * @param recipient
     *                �ռ��������ַ
     * @param subject
     *                �ʼ�����
     * @param content
     *                �ʼ�����
     * @throws AddressException
     * @throws MessagingException
     * @throws UnsupportedEncodingException 
     */
    public void send(String recipient, String subject, String content) throws AddressException, MessagingException, UnsupportedEncodingException {
    	// ����mime�����ʼ�
    	MimeMessage message = new MimeMessage(session);
    	// ���÷�����
    	message.setFrom(new InternetAddress(MailAuthenticator.USERNAME, MimeUtility.encodeText(MailAuthenticator.NAME)));
    	// �����ռ���
    	message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
    	// ��������
    	message.setSubject(subject);
    	// �����ʼ�����
    	message.setContent(content, "text/html;charset=utf-8");
    	// ����
    	Transport.send(message);
    }
 
    /**
     * Ⱥ���ʼ�
     * 
     * @param recipients
     *                �ռ�����
     * @param subject
     *                ����
     * @param content
     *                ����
     * @throws AddressException
     * @throws MessagingException
     * @throws UnsupportedEncodingException 
     */
    public void send(List<String> recipients, String subject, String content) throws AddressException, MessagingException, UnsupportedEncodingException {
    	// ����mime�����ʼ�
    	MimeMessage message = new MimeMessage(session);
    	// ���÷�����
    	message.setFrom(new InternetAddress(MailAuthenticator.USERNAME, MimeUtility.encodeText(MailAuthenticator.NAME)));
    	// �����ռ�����
    	int num = recipients.size();
    	InternetAddress[] addresses = new InternetAddress[num];
    	for (int i = 0; i < num; i++) {
    		addresses[i] = new InternetAddress(recipients.get(i));
    	}
    	message.setRecipients(RecipientType.TO, addresses);
    	// ��������
    	message.setSubject(subject);
    	// �����ʼ�����
    	message.setContent(content, "text/html;charset=utf-8");
    	// ����
    	Transport.send(message);
    }
 
}

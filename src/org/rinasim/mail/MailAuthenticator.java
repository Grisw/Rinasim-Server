/**
 * 
 */
package org.rinasim.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * �����������¼��֤
 * @author ������
 * @date 2015��3��18�� ����1:13:00
 * @since v1.0
 */
public class MailAuthenticator extends Authenticator {
	
	/**
	 * �����������û���
	 */
    public static final String USERNAME="rinasim@sina.com";

    /**
     * ��������
     */
    public static final String NAME="Rinasim";
    
    private static final String PASSWORD="rinasimwsl";
 
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
    	return new PasswordAuthentication(USERNAME, PASSWORD);
    }
 
}

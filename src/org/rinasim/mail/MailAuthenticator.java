/**
 * 
 */
package org.rinasim.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 服务器邮箱登录验证
 * @author 刘旭涛
 * @date 2015年3月18日 下午1:13:00
 * @since v1.0
 */
public class MailAuthenticator extends Authenticator {
	
	/**
	 * 服务器邮箱用户名
	 */
    public static final String USERNAME="rinasim@sina.com";

    /**
     * 服务器名
     */
    public static final String NAME="Rinasim";
    
    private static final String PASSWORD="rinasimwsl";
 
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
    	return new PasswordAuthentication(USERNAME, PASSWORD);
    }
 
}

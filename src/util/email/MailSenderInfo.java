package util.email;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.Getter;
import lombok.Setter;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by chentao on 2016/12/2.
 */
@Getter
@Setter
public class MailSenderInfo implements Serializable{
    /**
     * 邮件发送服务器
     * */
    private String mailSenderHost;
    /**
     * 邮件发送服务器端口,SSL加密发送默认端口465，非SSL发送默认端口25
     */
    private int mailSenderPort = 465;
    /**
     * 发送的邮箱是否需要身份认证，默认需要认证
     */
    private Boolean isAuth = Boolean.TRUE;
    /**
     * 认证的账号和密码
     */
    private String username;
    /**
     * 该密码为 发送邮件需要认证的密码，不是邮箱登录密码
     */
    private String password;
    /**
     * 发件地址
     */
    private String fromAddress;
    /**
     * 收件地址
     */
    private List<String> toAddress = new ArrayList<>();
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 附件名
     */
    private List<String> attacheFiles = new ArrayList<>();

    private static final String  MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    // 添加SSL支持
    private static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
    private static final String MAIL_SMTP_SSL_SOCKETFACTORY = "mail.smtp.ssl.socketFactory";

    public Properties getProperties () throws GeneralSecurityException {
        Properties properties = System.getProperties();
        properties.put(MAIL_SMTP_HOST, this.mailSenderHost);
        properties.put(MAIL_SMTP_AUTH, this.isAuth);
        /*
         * 添加SSL支持
         * QQ邮箱必须通过SSL发送，所以必须配置以下信息
         * 126邮箱可以不用配置
         */
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put(MAIL_SMTP_SSL_ENABLE, "true");
        properties.put(MAIL_SMTP_SSL_SOCKETFACTORY, sf);
        return properties;
    }

    public Authenticator getAuthentication () {
        return new StmpAuth();
    }

    /**
     * 身份认证
     */
    class StmpAuth extends Authenticator{

        protected PasswordAuthentication getAuthentication () {
            return new PasswordAuthentication(username, password);
        }
    }
}

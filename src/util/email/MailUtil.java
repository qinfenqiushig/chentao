package util.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;

/**
 * 邮件工具类
 *
 * Created by acer on 2016/12/4.
 */
public class MailUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

    public static boolean sendMail(MailSenderInfo mailInfo) {
        // 1.配置邮件发送服务器信息（发件服务器地址，认证信息等）
        Session session = null;
        Transport transport = null;
        boolean success = false;
        try {
            if (mailInfo.getIsAuth()) {
                session = Session.getInstance(mailInfo.getProperties(), mailInfo.getAuthentication());
            } else {
                session = Session.getDefaultInstance(mailInfo.getProperties());
            }
            session.setDebug(false);
            // 2.创建邮件消息对象
            Message mailMessage = new MimeMessage(session);
            // 邮件发送者
            mailMessage.setFrom(new InternetAddress(mailInfo.getFromAddress()));
            // 添加邮件接受者
            for (String toAddress : mailInfo.getToAddress()) {
                mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            }
            // 设置邮件主题
            mailMessage.setSubject(mailInfo.getSubject());

            // 设置邮件内容（正文、附件。。。）
            Multipart multipart = new MimeMultipart();

            // 邮件正文
            BodyPart bodyPart = new MimeBodyPart();
//            bodyPart.setText(mailInfo.getContent());
            bodyPart.setContent(mailInfo.getContent(), "text/html;charset=utf8");
            multipart.addBodyPart(bodyPart);
            // 添加附件
            for (String fileName : mailInfo.getAttacheFiles()) {
                MimeBodyPart filePart = new MimeBodyPart();
                File file = new File(fileName);
                filePart.setDataHandler(new DataHandler(new FileDataSource(file)));
                // 防止附件中文名称乱码
                filePart.setFileName(MimeUtility.encodeText(file.getName()));
                multipart.addBodyPart(filePart);
            }
            // 将所有邮件内容放入mesage中
            mailMessage.setContent(multipart);

            transport = session.getTransport("smtp");
            transport.connect(mailInfo.getMailSenderHost(), mailInfo.getMailSenderPort(), mailInfo.getUsername(), mailInfo.getPassword());
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
            LOGGER.info("邮件发送成功！");
            success = true;
        } catch (Exception e) {
            LOGGER.error("邮件发送失败", e);
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    LOGGER.error("关闭transport失败！", e);
                }
            }
        }
        return success;
    }
}

package com.zjzmjr.core.common.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮件发送
 * 
 * @author Administrator
 * @version $Id: MailUtil.java, v 0.1 2017-7-14 下午4:55:10 Administrator Exp $
 */
public class MailUtil {

    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private static final String fromMail = "wzchen86@163.com"; // 发送邮件地址/账号

    private static final String password = "wzchen85"; // 邮箱密码

    private static final String type = "smtp"; // 请求方式

    private static final String smtp = "smtp.163.com"; // 服务器地址

    /**
     * 发送邮件
     * 
     * @param text
     * @param subject
     * @param mail
     * @return
     */
    public static boolean sendTxtMail(String text, String subject, String mail) {
        Properties props = new Properties();

        Session session = Session.getInstance(props, null);
        session.setDebug(true); // 打开debug模式，会打印发送细节到console
        Message message = new MimeMessage(session); // 实例化一个MimeMessage集成自abstract
                                                    // Message 。参数为session
        try {
            message.setFrom(new InternetAddress(fromMail));
            message.setText(text);
            message.setSubject(subject);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail)); // 设置接收方
            Transport transport = session.getTransport(type);
            transport.connect(smtp, fromMail, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException e) {
            logger.error("邮件发送失败", e);
            return false;
        } catch (MessagingException e) {
            logger.error("邮件发送失败", e);
            return false;
        }
        return true;
    }

}

package com.itheima.utils;

import com.itheima.model.domain.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class EmailUtil {
    //链接邮件服务器
    public static Session creatEmailConnection() throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.163.com");
        properties.setProperty("mail.smtp.port","25");
        properties.setProperty("mail.smtp.auth","true");

        //获取Session
        Session session = Session.getInstance(properties);
        session.setDebug(false);

        return session;
    }


    //创建邮件
    public static Message creatTextEmail(Session session, Email email) throws MessagingException {
        MimeMessage message = new MimeMessage(session);

        String text = email.getText();
        String subText = email.getSub();
        String toEmail = email.getToEmail();

        message.setFrom("18993134862@163.com");
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
        message.setSubject(subText);
        message.setSentDate(new Date());
        message.setText(text);
        return message;
    }

    public static Transport creatTransport(Session session,Message message) throws MessagingException {
        Transport transport = session.getTransport();
        transport.connect("smtp.163.com","18993134862","OZOBHSEHZNITCPXN");
        return transport;
    }

    public static void sendEmail(Email email) throws MessagingException {
        Session session = EmailUtil.creatEmailConnection();
        Message message = EmailUtil.creatTextEmail(session,email);
        Transport transport = EmailUtil.creatTransport(session,message);
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }
}

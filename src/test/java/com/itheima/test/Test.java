package com.itheima.test;

import com.itheima.dao.ArticleMapper;
import com.itheima.dao.StatisticMapper;
import com.itheima.model.domain.Article;
import com.itheima.model.domain.Email;
import com.itheima.service.impl.ArticleServiceImpl;
import com.itheima.utils.EmailUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.*;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private StatisticMapper statisticMapper;

    @org.junit.Test
    public void cc(){
        statisticMapper.updateArticleFavorCountWithId(1,2);
    }


//    @org.junit.Test
//    public void c2(){
//        FavorController favorController = new FavorController();
//        Article article = new Article();
//        article.setId(1);
//        articleService.favorCountUP(1,4);
//
//    }

    @org.junit.Test
    public void csac(){
        boolean c = articleService.isFavor(1,"admin");
        System.out.println(c);
    }


    @org.junit.Test
    public void push() throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.163.com");
        properties.setProperty("mail.smtp.port","25");
        properties.setProperty("mail.smtp.auth","true");


        Session session = Session.getInstance(properties);
        session.setDebug(true);

        Transport transport = session.getTransport();
        transport.connect("smtp.163.com","18993134862","OZOBHSEHZNITCPXN");
        Message message = EmailUtil.creatTextEmail(session,new Email());

        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

    @org.junit.Test
    public void pp() throws MessagingException{
        Email email = new Email();
        email.setSub("消息提示");
        email.setText("你的博客被人回复了");
        email.setToEmail("1780953914@qq.com");
        EmailUtil.sendEmail(email);
    }
}

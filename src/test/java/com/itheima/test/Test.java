package com.itheima.test;

import com.itheima.dao.ArticleMapper;
import com.itheima.model.domain.Article;
import com.itheima.service.impl.ArticleServiceImpl;
import com.itheima.web.client.FavorController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleServiceImpl articleService;

    @org.junit.Test
    public void cc(){
        Article article = articleMapper.selectArticleWithId(3);
        System.out.println(article.getFavorCount());
        article.setFavorCount(article.getFavorCount() + 1);
        Integer id = article.getId();
        articleMapper.favorNumberUP(article.getFavorCount(),id);
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
}

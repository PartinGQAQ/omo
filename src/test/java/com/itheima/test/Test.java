package com.itheima.test;

import com.itheima.dao.ArticleMapper;
import com.itheima.model.domain.Article;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
    @Autowired
    private ArticleMapper articleMapper;

    @org.junit.Test
    public void cc(){
        Article article = articleMapper.selectArticleWithId(3);
        System.out.println(article.getFavorCount());
        article.setFavorCount(article.getFavorCount() + 1);
        Integer id = article.getId();
        articleMapper.favorNumberUP(article.getFavorCount(),id);
    }
}

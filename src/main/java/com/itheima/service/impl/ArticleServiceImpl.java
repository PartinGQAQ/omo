package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.ArticleMapper;
import com.itheima.dao.CommentMapper;
import com.itheima.dao.StatisticMapper;
import com.itheima.dao.UserMapper;
import com.itheima.model.domain.Article;
import com.itheima.model.domain.Statistic;
import com.itheima.model.domain.User;
import com.itheima.service.IArticleService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Classname ArticleServiceImpl
 * @Description TODO
 * @Date 2019-3-14 9:47
 * @Created by CrazyStone
 */
@Service
@Transactional
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    // 分页查询文章列表
    @Override
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Article> articleList = articleMapper.selectArticleWithPage();
        // 封装文章统计数据
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        PageInfo<Article> pageInfo=new PageInfo<>(articleList);
        return pageInfo;
    }

    // 统计前10的热度文章信息
    @Override
    public List<Article> getHeatArticles( ) {
        List<Statistic> list = statisticMapper.getStatistic();
        List<Article> articlelist=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Article article = articleMapper.selectArticleWithId(list.get(i).getArticleId());
            article.setHits(list.get(i).getHits());
            article.setCommentsNum(list.get(i).getCommentsNum());
            articlelist.add(article);
            if(i>=9){
                break;
            }
        }
        return articlelist;
    }

    // 根据id查询单个文章详情，并使用Redis进行缓存管理
    public Article selectArticleWithId(Integer id){
        Article article = null;
        Object o = redisTemplate.opsForValue().get("article_" + id);

        //查询是否已经点赞过了
        if(o!=null){
            article=(Article)o;
        }else{
            article = articleMapper.selectArticleWithId(id);
            if(article!=null){
                redisTemplate.opsForValue().set("article_" + id,article);
            }
        }
        return article;
    }

    // 发布文章
    @Override
    public void publish(Article article) {
        // 去除表情
        article.setContent(EmojiParser.parseToAliases(article.getContent()));
        article.setCreated(new Date());
        article.setHits(0);
        article.setCommentsNum(0);
        // 插入文章，同时插入文章统计数据
        articleMapper.publishArticle(article);
        statisticMapper.addStatistic(article);
    }

    // 更新文章
    @Override
    public void updateArticleWithId(Article article) {
        article.setModified(new Date());
        articleMapper.updateArticleWithId(article);
        redisTemplate.delete("article_" + article.getId());
    }

    // 删除文章
    @Override
    public void deleteArticleWithId(int id) {
        // 删除文章的同时，删除对应的缓存
        articleMapper.deleteArticleWithId(id);
        redisTemplate.delete("article_" + id);
        // 同时删除对应文章的统计数据
        statisticMapper.deleteStatisticWithId(id);
        // 同时删除对应文章的评论数据
        commentMapper.deleteCommentWithId(id);
    }

    //点赞增加
    @Override
    public Map<String, Object> favorCountUP(Article article, String userName) {
        //获取用户的ID
        User user = userMapper.findByUsername(userName);
        Integer user_id = user.getId();

        //获取文章
        Integer article_id = article.getId();
        article = articleMapper.selectArticleWithId(article_id);
        //当前点赞数增加
        article.setFavorCount(article.getFavorCount() + 1);
        //当前的点赞增加
        articleMapper.favorNumberUP(article.getFavorCount(),article_id);
        //更新点赞关系表
        articleMapper.addFavor(article_id,user_id);

        //更新统计表
        statisticMapper.updateArticleFavorCountWithId(article.getFavorCount(),article_id);

        //更新redis中的article
        redisTemplate.delete("article_" + article.getId());

        //返回信息
        Map<String,Object> res = new HashMap<>();
        res.put("code",200);
        res.put("message","点赞成功");

        System.out.println("点赞了👍");
        return res;
    }

    //点赞减少
    @Override
    public Map<String, Object> favorCountDOWN(Article article, String userName) {
        //获取用户的ID
        User user = userMapper.findByUsername(userName);
        Integer user_id = user.getId();

        //获取文章
        Integer article_id = article.getId();
        article = articleMapper.selectArticleWithId(article.getId());
        //当前点赞数减少
        article.setFavorCount(article.getFavorCount() - 1);
        //当前的点赞减少
        articleMapper.favorNumberUP(article.getFavorCount(),article_id);
        //更新点赞关系表
        articleMapper.cancelFavor(article_id,user_id);

        //更新统计表
        statisticMapper.updateArticleFavorCountWithId(article.getFavorCount(),article_id);

        //缓存更新
        redisTemplate.delete("article_" + article.getId());

        System.out.println("取消点赞");
        //返回信息
        Map<String,Object> res = new HashMap<>();
        res.put("code",200);
        res.put("message","取消点赞");
        return res;
    }

    @Override
    public Boolean isFavor(Integer article_id, String userName) {

        //获取用户的ID
        User user = userMapper.findByUsername(userName);
        Integer user_id = user.getId();

        //获取是否
        Integer isFavor = articleMapper.selectFavor(article_id,user_id);
        if (isFavor == null){
            return false;
        }else {
            return true;
        }
    }

}


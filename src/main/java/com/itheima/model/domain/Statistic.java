package com.itheima.model.domain;

/**
 * 文章相关动态数据统计实体类
 * */
public class Statistic {
    private Integer id;
    private Integer articleId;   // 评论的文章id
    private Integer hits;        // 点击量
    private Integer commentsNum;// 评论总量

    public Statistic(Integer id, Integer articleId, Integer hits, Integer commentsNum, Integer favorCount) {
        this.id = id;
        this.articleId = articleId;
        this.hits = hits;
        this.commentsNum = commentsNum;
        this.favorCount = favorCount;
    }

    private Integer favorCount;

    public Integer getFavorCount() {
        return favorCount;
    }

    public void setFavorCount(Integer favorCount) {
        this.favorCount = favorCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }
}

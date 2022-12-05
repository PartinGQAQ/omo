package com.itheima.web.client;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.Article;
import com.itheima.model.domain.Comment;
import com.itheima.service.IArticleService;
import com.itheima.service.ICommentService;
import com.itheima.service.ISiteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname IndexController
 * @Description TODO
 * @Date 2019-3-14 9:49
 * @Created by CrazyStone
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IArticleService articleServiceImpl;
    @Autowired
    private ICommentService commentServiceImpl;
    @Autowired
    private ISiteService siteServiceImpl;

    // 博客首页，会自动跳转到文章页
    @GetMapping(value = "/")
    private String index(HttpServletRequest request) {
        return this.index(request, 1, 5);
    }

    // 文章页
    @GetMapping(value = "/page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "5") int count) {
        PageInfo<Article> articles = articleServiceImpl.selectArticleWithPage(page, count);
        // 获取文章热度统计信息
        List<Article> articleList = articleServiceImpl.getHeatArticles();
        request.setAttribute("articles", articles);
        request.setAttribute("articleList", articleList);
        logger.info("分页获取文章信息: 页码 "+page+",条数 "+count);
        return "client/index";
    }

    // 文章详情查询
    @GetMapping(value = "/article/{id}")
    public String getArticleById(@PathVariable("id") Integer id, HttpServletRequest request){
        Article article = articleServiceImpl.selectArticleWithId(id);
        if(article!=null){
            //查询当前用户
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();



            // 查询封装评论相关数据
            getArticleComments(request, article);
            // 更新文章点击量
            siteServiceImpl.updateStatistics(article);
            request.setAttribute("article",article);

            //查询是否点过赞
            if (authentication.getPrincipal() != "anonymousUser"){
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                if(articleServiceImpl.isFavor(article.getId(), userDetails.getUsername())){
                    //如果登录点过赞返回1
                    request.setAttribute("isFavor",1);
                }else {
                    //如果没点过赞就返回0
                    request.setAttribute("isFavor",0);
                }
            }else {
                //如果没有登录返回一个0
                request.setAttribute("isFavor",0);
            }

            return "client/articleDetails";
        }else {
            logger.warn("查询文章详情结果为空，查询文章id: "+id);
            // 未找到对应文章页面，跳转到提示页
            return "comm/error_404";
        }
    }

    // 查询文章的评论信息，并补充到文章详情里面
    private void getArticleComments(HttpServletRequest request, Article article) {
        if (article.getAllowComment()) {
            // cp表示评论页码，commentPage
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            request.setAttribute("cp", cp);
            PageInfo<Comment> comments = commentServiceImpl.getComments(article.getId(),Integer.parseInt(cp),3);
            request.setAttribute("cp", cp);
            request.setAttribute("comments", comments);
        }
    }

}


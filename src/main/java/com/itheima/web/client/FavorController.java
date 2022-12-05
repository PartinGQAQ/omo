package com.itheima.web.client;


import com.itheima.dao.ArticleMapper;
import com.itheima.dao.UserMapper;
import com.itheima.model.domain.Article;
import com.itheima.service.IArticleService;
import com.itheima.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class FavorController {

    @Resource
    private ArticleServiceImpl articleService;

    @PostMapping("/Favor")
    public Map<String,Object> addFavor(Article article){
        //获取当前的用户
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //根据用户的名字获取id
        String userName = userDetails.getUsername();
        Map<String,Object> res = articleService.favorCountUP(article,userName);
        res.put("code","200");
        res.put("message","点赞成功");
        return res;
    }

    @PostMapping("/cancleFavor")
    public Map<String,Object> cancleFavor(Article article){
        //获取当前的用户
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //根据用户的名字获取id
        String userName = userDetails.getUsername();
        Map<String,Object> res = articleService.favorCountDOWN(article,userName);
        res.put("code","取消点赞");
        res.put("message","取消点赞");
        return res;
    }
}

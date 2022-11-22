package com.itheima.service.impl;

import com.itheima.dao.UserMapper;
import com.itheima.model.domain.User;
import com.itheima.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isRegisted(User user) {
        if (userMapper.findByUsername(user.getUsername()) == null && userMapper.findByEmail(user.getEmail()) == null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Map<String,Object> RegisterUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Map<String,Object> res = new HashMap<>();
        Date date = new java.sql.Date(new Date().getTime());
        user.setCreated(date);
        user.setEx_point(1);
        user.setLevel(1);
        user.setValid(1);
        user.setPassword(encoder.encode(user.getPassword()));
        if (isRegisted(user)) {
            userMapper.insertUser(user);
            int id = userMapper.findByUsername(user.getUsername()).getId();
            userMapper.insertAuthority(id,2);
            res.put("code",200);
            res.put("message","注册成功,前往登录页面登录");
        }else{
            res.put("code",400);
            res.put("message","注册失败,账号或者邮箱已经被注册");
        }
        return res;
    }
}

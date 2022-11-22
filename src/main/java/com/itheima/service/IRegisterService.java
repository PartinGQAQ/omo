package com.itheima.service;

import com.itheima.model.domain.User;

import java.util.Map;

public interface IRegisterService {
    //查看是否被注册
    public boolean isRegisted(User user);
    //返回true就是没注册false就是已经注册了
    //进行注册
    public Map RegisterUser(User user);
}

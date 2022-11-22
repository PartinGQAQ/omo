package com.itheima.web.client;

import com.itheima.model.domain.User;
import com.itheima.service.impl.RegisterServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
@RestController
@RequestMapping("/user")
public class RegisterController {
    @Resource
    private RegisterServiceImpl registerService;

//    @GetMapping(value = "/registerUser")
//    public String registerUser(@RequestParam("username") String username,
//                                           @RequestParam("password") String password,
//                                           @RequestParam("email") String email){
//
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//        System.out.println(user.toString());
//        Map<String,Object> map =  registerService.RegisterUser(user);
//        if (Integer.parseInt(String.valueOf(map.get("code"))) == 200){
//            return "comm/login";
//        }else {
//            return "loginAndRegister/register";
//        }
//
//    }

    @PostMapping(value = "/registerUserPost")
    public Map<String, Object> register(User user){
        Map<String,Object> map = registerService.RegisterUser(user);
        return map;
    }
}

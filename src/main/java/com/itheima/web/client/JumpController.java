package com.itheima.web.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JumpController {
    @GetMapping(value = "/toRegister")
    public String toRegister(){
        return "loginAndRegister/register";
    }
}

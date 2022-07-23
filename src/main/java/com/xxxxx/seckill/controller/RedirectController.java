package com.xxxxx.seckill.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname RedirectController
 * @Description
 * @Version 1.0.0
 * @Date 2022/7/24 4:36 AM
 * @Created by weivang
 */
@RequestMapping("/")
public class RedirectController {

    /*
     * 方法描述: 重定向登录页
     * @since: 1.0
     * @param: none
     * @return: java.lang.String
     * @author: vang
     * @date: 2022/7/24
     */
    @GetMapping("/j")
    public String redirectHome(){
        System.out.println("jik");
        return "redirect:login/toLogin";
    }
}

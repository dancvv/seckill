package com.xxxxx.seckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wang
 * @Description: TO-DO
 * @Date: 2022/04/25/1:56 AM
 */

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    /**
     * 方法描述: 功能登录界面
     * @since: 1.0
     * @param: []
     * @return: java.lang.String
     * @author: vang
     * @date: 2022/4/25
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/doLogin")
    public String doLogin(){
        return "login";
    }
}

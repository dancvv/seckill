package com.xxxxx.seckill.controller;

import com.xxxxx.seckill.service.IUserService;
import com.xxxxx.seckill.vo.LoginVo;
import com.xxxxx.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wang
 * @Description: TO-DO
 * @Date: 2022/04/25/1:56 AM
 */

@Controller
@CrossOrigin
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService iUserService;

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

    /**
     * 方法描述: 登录功能
     * @since: 1.0
     * @param: [loginVo]
     * @return: com.xxxxx.seckill.vo.RespBean
     * @author: vang
     * @date: 2022/4/28
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(LoginVo loginVo){
        log.info("{}", loginVo);
        return iUserService.doLogin(loginVo);
    }
}

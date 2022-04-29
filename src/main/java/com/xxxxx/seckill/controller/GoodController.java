package com.xxxxx.seckill.controller;

import com.xxxxx.seckill.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Classname Controller
 * @Description
 * @Version 1.0.0
 * @Date 2022/4/29 2:15 AM
 * @Created by weivang
 */

@Controller
@RequestMapping("/goods")
public class GoodController {
    /**
     * 方法描述: 跳转商品页面
     * @since: 1.0
     * @param: [session, model, ticket]
     * @return: java.lang.String
     * @author: vang
     * @date: 2022/4/29
     */
    @RequestMapping("toList")
    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String ticket){
//        通过session获取用户，并判断是否存在
        if(!StringUtils.hasLength(ticket)){
            return "login";
        }
        System.out.println(session);
        User user = (User) session.getAttribute(ticket);
        if(null == user) return "login";
        model.addAttribute("user", user);
        return "goodsList";
    }
}

package com.xxxxx.seckill.controller;

import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.vo.RespBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wang
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用户信息测试，压力测试
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }
}

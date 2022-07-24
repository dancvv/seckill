package com.xxxxx.seckill.controller;

import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.rabbitmq.MQSender;
import com.xxxxx.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private MQSender mqSender;
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

    /*
     * 方法描述: 测试rabbit mq发送消息
     * @since: 1.0
     * @param: []
     * @return: void
     * @author: weivang
     * @date: 2022/7/24
     */
    @GetMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send("name");
    }

    @GetMapping("/mq/fanout")
    @ResponseBody
    public void mq01(){
        mqSender.send("Hello");
    }
}

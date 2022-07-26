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
@ResponseBody
public class UserController {

    @Autowired
    private MQSender mqSender;
    /**
     * 用户信息测试，压力测试
     * @param user
     * @return
     */
    @RequestMapping("/info")
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
    public void mq(){
        mqSender.send("From the Controller");
    }

    //@GetMapping("/mq/fanout")
    //public void mq01(){
    //    mqSender.send("Hello, this is the controller message");
    //}

    /*
     * 方法描述: 发送rabbit mq消息
     * @since: 1.0
     * @param: []
     * @return: void
     * @author: weivang
     * @date: 2022/7/27
     */
    @GetMapping("/mq/direct01")
    public void mq01(){
        mqSender.send01("Hello, this is from direct 01");
    }
    @GetMapping("/mq/direct02")
    public void mq02(){
        mqSender.send02("Hello, this is from direct 02");
    }

}

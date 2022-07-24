package com.xxxxx.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname MQSender
 * @Description
 * @Version 1.0.0
 * @Date 2022/7/24 5:38 PM
 * @Created by weivang
 */
@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object msg){
        log.info("发送消息："+ msg);
        rabbitTemplate.convertAndSend("fanoutExchange", msg);
    }
}

package com.xxxxx.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname MQReceiver
 * @Description
 * @Version 1.0.0
 * @Date 2022/7/24 5:45 PM
 * @Created by weivang
 */
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接收消息：" + msg);
        rabbitTemplate.convertAndSend("queue", msg);
    }

    @RabbitListener(queues = "queue_fanout01")
    public void receive01(Object msg){
        log.info("QUEUE01接收消息:" + msg);
    }

    @RabbitListener(queues = "queue_fanout02")
    public void receive02(Object msg){
        log.info("QUEUE02接收消息：" + msg);
    }
}

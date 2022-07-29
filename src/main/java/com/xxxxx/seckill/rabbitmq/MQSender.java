package com.xxxxx.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
        rabbitTemplate.convertAndSend("fanoutExchange","", msg);
    }

    /*
     * 方法描述: 发送消息给接收机
     * @since: 1.0
     * @param: [msg]
     * @return: void
     * @author: weivang
     * @date: 2022/7/27
     */
    public void send01(Object msg){
        log.info("发送green消息" + msg);
        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
    }
    public void send02(Object msg){
        log.info("发送green消息" + msg);
        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
    }

    /*
     * 方法描述: topic模式发送测试
     * @since: 1.0
     * @param: [msg]
     * @return: void
     * @author: weivang
     * @date: 2022/7/28
     */
    public void send03(Object msg){
        log.info("发送消息(被01队列接受)" + msg);
        rabbitTemplate.convertAndSend("topicExchange", "queue.red.message", msg);
    }
    public void send04(Object msg){
        log.info("发送消息（被两个queue接受）" + msg);
        rabbitTemplate.convertAndSend("topicExchange", "message.queue.green.abc", msg);
    }

    /*
     * 方法描述: header模式下发送
     * @since: 1.0
     * @param: [msg]
     * @return: void
     * @author: weivang
     * @date: 2022/7/28
     */
    public void sendHeader01(String msg){
        log.info("发送消息（被两个queue接受）：" + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("color", "red");
        properties.setHeader("speed", "fast");
        Message message = new Message(msg.getBytes(), properties);
        rabbitTemplate.convertAndSend("headersExchange", "", message);
    }

    public void sendHeader02(String msg){
        log.info("发送消息（被01队列接受）：" + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("color", "red");
        properties.setHeader("speed", "normal");
        Message message = new Message(msg.getBytes(), properties);
        rabbitTemplate.convertAndSend("headersExchange", "", message);
    }
}

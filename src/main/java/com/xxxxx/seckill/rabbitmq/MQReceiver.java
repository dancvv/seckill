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

    /*
     * 方法描述: convertAndSend不适合在此处引入，会出现重大bug
     * @since: 1.0
     * @param: [msg]
     * @return: void
     * @author: weivang
     * @date: 2022/7/25
     */
    @RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接收消息：" + msg);
        //rabbitTemplate.convertAndSend("queue", msg);
    }

    //@RabbitListener(queues = "queue_fanout01")
    //public void receive01(Object msg){
    //    log.info("QUEUE01接收消息:" + msg);
    //}
    //
    //@RabbitListener(queues = "queue_fanout02")
    //public void receive02(Object msg){
    //    log.info("QUEUE02接收消息：" + msg);
    //}
    /*
     * 方法描述: direct模式下的消息接受
     * @since: 1.0
     * @param: [msg]
     * @return: void
     * @author: weivang
     * @date: 2022/7/27
     */
    @RabbitListener(queues = "queue_direct01")
    public void receive01(Object msg){
        log.info("QUEUE01接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_direct02")
    public void receive02(Object msg){
        log.info("QUEUE01接收消息：" + msg);
    }
    /*
     * 方法描述: mq 接受消息
     * @since: 1.0
     * @param: [msg]
     * @return: void
     * @author: weivang
     * @date: 2022/7/27
     */
    @RabbitListener(queues = "queue_topic01")
    public void receiveMQ1(Object msg){
        log.info("QUEUE01接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_topic02")
    public void receiveMQ2(Object msg){
        log.info("QUEUE02 OK接收消息：" + msg);
    }
}

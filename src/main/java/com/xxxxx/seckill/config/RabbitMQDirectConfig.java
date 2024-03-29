package com.xxxxx.seckill.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RabbitMQDirectConfig
 * @Description
 * @Version 1.0.0
 * @Date 2022/7/27 12:52 AM
 * @Created by weivang
 */
//@Configuration
public class RabbitMQDirectConfig {
    //
    //private static final String QUEUE01 = "queue_direct01";
    //private static final String QUEUE02 = "queue_direct02";
    //private static final String EXCHANGE = "directExchange";
    //private static final String ROUTINGKEY01 = "queue.red";
    //private static final String ROUTINGKEY02 = "queue.green";
    //
    //@Bean
    //public Queue queue01(){
    //    return new Queue(QUEUE01);
    //}
    //@Bean
    //public Queue queue02(){
    //    return new Queue(QUEUE02);
    //}
    //
    ///*
    // * 方法描述: 直接交换机
    // * @since: 1.0
    // * @param: []
    // * @return: org.springframework.amqp.core.DirectExchange
    // * @author: weivang
    // * @date: 2022/7/27
    // */
    //@Bean
    //public DirectExchange directExchange(){
    //    return new DirectExchange(EXCHANGE);
    //}
    ///*
    // * 方法描述: 绑定路由，相应的routing key
    // * @since: 1.0
    // * @param: []
    // * @return: org.springframework.amqp.core.Binding
    // * @author: weivang
    // * @date: 2022/7/27
    // */
    //@Bean
    //public Binding binding01(){
    //    return BindingBuilder.bind(queue01()).to(directExchange()).with(ROUTINGKEY01);
    //}
    //@Bean
    //public Binding binding02(){
    //    return BindingBuilder.bind(queue02()).to(directExchange()).with(ROUTINGKEY02);
    //}
}

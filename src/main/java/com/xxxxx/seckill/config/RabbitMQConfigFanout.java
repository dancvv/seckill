package com.xxxxx.seckill.config;

import org.springframework.context.annotation.Configuration;

/**
 * @Classname RabbitMQConfig
 * @Description rabbit mq 配置类 fanout 配置
 * @Version 1.0.0
 * @Date 2022/7/24 5:27 PM
 * @Created by weivang
 */
//@Configuration
public class RabbitMQConfigFanout {
    //private static final String QUEUE01 = "queue_fanout01";
    //private static final String QUEUE02 = "queue_fanout02";
    //private static final String EXCHANGE = "fanoutExchange";
    //
    //@Bean
    //public Queue queue(){
    //    // 持久化
    //    return new Queue("queue", true);
    //}
    //@Bean
    //public Queue queue01(){
    //    return new Queue(QUEUE01);
    //}
    //@Bean
    //public Queue queue02(){
    //    return new Queue(QUEUE02);
    //}
    //@Bean
    //public FanoutExchange fanoutExchange(){
    //    return new FanoutExchange(EXCHANGE);
    //}
    //// 队列绑定 绑定到交换机上
    //@Bean
    //public Binding binding01(){
    //    return BindingBuilder.bind(queue01()).to(fanoutExchange());
    //}
    //@Bean Binding binding02(){
    //    return BindingBuilder.bind(queue02()).to(fanoutExchange());
    //}
}

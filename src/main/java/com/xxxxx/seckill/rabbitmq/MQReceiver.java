package com.xxxxx.seckill.rabbitmq;

import com.rabbitmq.tools.json.JSONUtil;
import com.xxxxx.seckill.entity.SeckillMessage;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.service.IOrderService;
import com.xxxxx.seckill.service.impl.GoodsServiceImpl;
import com.xxxxx.seckill.utils.JsonUtil;
import com.xxxxx.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Autowired
    private GoodsServiceImpl goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;
    /*
     * 方法描述: convertAndSend不适合在此处引入，会出现重大bug
     * @since: 1.0
     * @param: [msg]
     * @return: void
     * @author: weivang
     * @date: 2022/7/25
     */
    //@RabbitListener(queues = "queue")
    //public void receive(Object msg){
    //    log.info("接收消息：" + msg);
    //    //rabbitTemplate.convertAndSend("queue", msg);
    //}
    //
    ////@RabbitListener(queues = "queue_fanout01")
    ////public void receive01(Object msg){
    ////    log.info("QUEUE01接收消息:" + msg);
    ////}
    ////
    ////@RabbitListener(queues = "queue_fanout02")
    ////public void receive02(Object msg){
    ////    log.info("QUEUE02接收消息：" + msg);
    ////}
    ///*
    // * 方法描述: direct模式下的消息接受
    // * @since: 1.0
    // * @param: [msg]
    // * @return: void
    // * @author: weivang
    // * @date: 2022/7/27
    // */
    //@RabbitListener(queues = "queue_direct01")
    //public void receive01(Object msg){
    //    log.info("QUEUE01接收消息：" + msg);
    //}
    //
    //@RabbitListener(queues = "queue_direct02")
    //public void receive02(Object msg){
    //    log.info("QUEUE01接收消息：" + msg);
    //}
    ///*
    // * 方法描述: mq 接受消息
    // * @since: 1.0
    // * @param: [msg]
    // * @return: void
    // * @author: weivang
    // * @date: 2022/7/27
    // */
    //@RabbitListener(queues = "queue_topic01")
    //public void receiveMQ1(Object msg){
    //    log.info("QUEUE01接收消息：" + msg);
    //}
    //
    //@RabbitListener(queues = "queue_topic02")
    //public void receiveMQ2(Object msg){
    //    log.info("QUEUE02 OK接收消息：" + msg);
    //}
    //
    ///*
    // * 方法描述: header模式下接受消息
    // * @since: 1.0
    // * @param: [message]
    // * @return: void
    // * @author: weivang
    // * @date: 2022/7/28
    // */
    //@RabbitListener(queues = "queue_header01")
    //public void receive01(Message message){
    //    log.info("QUEUE01接受Message对象：" + message);
    //    log.info("QUEUE01接受消息：" + new String(message.getBody()));
    //}
    //
    //@RabbitListener(queues = "queue_header02")
    //public void receive02(Message message){
    //    log.info("QUEUE02接受Message对象：" + message);
    //    log.info("QUEUE02接受消息：" + new String(message.getBody()));
    //}


    @RabbitListener(queues = "seckillQueue")
    public void recevieSeckill(String msg){
        log.info("QUEUE接受消息：" + msg);
        SeckillMessage message = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        Long goodsId = message.getGoodsId();
        User user = message.getUser();
        // 根据id获取商品对象
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
    //   判断库存
        if(goods.getStockCount() < 1){
            return;
        }
    //    判断是否重复抢购
    //    存入redis中
        String seckillOrderJson =(String) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(StringUtils.hasLength(seckillOrderJson)){
            return;
        }
        // 下单操作
        orderService.seckill(user, goods);
    }

}

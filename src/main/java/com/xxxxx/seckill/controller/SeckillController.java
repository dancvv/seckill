package com.xxxxx.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxxx.seckill.entity.Order;
import com.xxxxx.seckill.entity.SeckillMessage;
import com.xxxxx.seckill.entity.SeckillOrder;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.rabbitmq.MQSender;
import com.xxxxx.seckill.service.IGoodsService;
import com.xxxxx.seckill.service.IOrderService;
import com.xxxxx.seckill.service.ISeckillGoodsService;
import com.xxxxx.seckill.service.ISeckillOrderService;
import com.xxxxx.seckill.utils.JsonUtil;
import com.xxxxx.seckill.vo.GoodsVo;
import com.xxxxx.seckill.vo.RespBean;
import com.xxxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MQSender mqSender;
    //内存优化，使用map内存标记
    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();
    /**
     * mac QPS:387.3
     * linux QPS:345.7
     * 秒杀功能
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckillDepreached")
    public String doSeckill2(Model model, User user, Long goodsId){
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//        判断是否有库存
        if(goodsVo.getStockCount() < 1){
//            没有库存
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK);
            return "seckillFail";
        }
//        判断是否重复抢购
        SeckillOrder seckill = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if(seckill != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "seckillFail";
        }
        Order order = orderService.seckill(user, goodsVo);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVo);
        return "orderDetail";
    }
    /**
     * mac QPS:387.3
     * linux QPS:345.7
     * linux 优化之后的QPS:399.8
     * 优化之后的代码，QPS提升15.7%
     * 秒杀功能
     * 数据库需要同步设置索引值
     * @param
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/doSeckill")
    @ResponseBody
    public RespBean doSeckill(User user, Long goodsId){
//        System.out.println(user);
        if(user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        /*
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//        判断是否有库存
        if(goodsVo.getStockCount() < 1){
//            没有库存
//            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
//            视频进度9：22秒
        }
//        判断是否重复抢购
//        可以不用使用sql语句查询抢购订单
//        SeckillOrder seckill = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
//        需要进行反序列化
        SeckillOrder seckillOrder= (SeckillOrder)redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(seckillOrder != null) {
//            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
//            42p,时间进度10:41
//            System.out.println("not ok");
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
//        System.out.println("start seckill");
        Order order = orderService.seckill(user, goodsVo);
        return RespBean.success(order);
         */
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(seckillOrder != null){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        // 内存标记，减少redis的访问
        if(EmptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 预减库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if(stock < 0){
            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 接入mq，消息消费
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return RespBean.error(RespBeanEnum.SESSION_ERROR);
    }
    /*
     * 方法描述: 获取秒杀结果
     * @since: 1.0
     * @param: [user, goodsId]
     * @return: com.xxxxx.seckill.vo.RespBean
     * 成功-1 秒杀失败, 0: 排队中
     * @author: weivang
     * @date: 2022/8/1
     */
    @GetMapping("/result")
    public RespBean getResult(User user, Long goodsId){
        if(user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }

    /*
     * 方法描述: bean的实例化时间
     * @since: 1.0
     * @param: []
     * @return: void
     * @author: weivang
     * @date: 2022/8/1
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(goodsVo -> {
            // 遍历每一个元素
            // 如果秒杀库存已经空了，直接内存标记
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            EmptyStockMap.put(goodsVo.getId(), false);
        });
    }
}

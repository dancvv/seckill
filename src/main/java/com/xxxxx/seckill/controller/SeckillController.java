package com.xxxxx.seckill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wf.captcha.ArithmeticCaptcha;
import com.xxxxx.seckill.config.AccessLimit;
import com.xxxxx.seckill.entity.Order;
import com.xxxxx.seckill.entity.SeckillMessage;
import com.xxxxx.seckill.entity.SeckillOrder;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.exception.GlobalException;
import com.xxxxx.seckill.rabbitmq.MQSender;
import com.xxxxx.seckill.service.IGoodsService;
import com.xxxxx.seckill.service.IOrderService;
import com.xxxxx.seckill.service.ISeckillGoodsService;
import com.xxxxx.seckill.service.ISeckillOrderService;
import com.xxxxx.seckill.utils.JsonUtil;
import com.xxxxx.seckill.vo.GoodsVo;
import com.xxxxx.seckill.vo.RespBean;
import com.xxxxx.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seckill")
@Slf4j
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
    @Autowired
    private RedisScript<Long> script;
    //内存优化，使用map内存标记
    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();
    /*
     * 方法描述: 获取路径
     * 使用注解
     * @since: 1.0
     * @param: [user, goodsId]
     * @return: com.xxxxx.seckill.vo.RespBean
     * @author: weivang
     * @date: 2022/8/7
     */
    @AccessLimit(second = 5, maxCount = 5, needLogin = true)
    @GetMapping("/path")
    @ResponseBody
    public RespBean getPath(User user, Long goodsId, String captcha, HttpServletRequest request){
        if(user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        /*
        * 使用注解优化
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 限制访问次数，5s内访问5次
        String uri = request.getRequestURI();
        Integer count = (Integer) valueOperations.get(uri + ":" + user.getId());
        System.out.println(count);
        if(count == null){
            valueOperations.set(uri + ":" + user.getId(), 1, 10, TimeUnit.SECONDS);
        }else if(count < 5){
            valueOperations.increment(uri + ":" + user.getId());
        }else{
            // 如果点击超过5次
            return RespBean.error(RespBeanEnum.ACCESS_LIMIT_REACHED);
        }
         */
        Boolean check = orderService.checkCaptcha(user, goodsId, captcha);
        if(!check){
            return RespBean.error(RespBeanEnum.ERROR_CAPTCHA);
        }
        String str = orderService.createPath(user, goodsId);
        return RespBean.success(str);

    }
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
     * mq优化：QPS:543.8
     * 历史错误优化，是由于服务器版本未更新
     * mac 14 medium 优化之后 QPS:237.8
     * 秒杀功能
     * 数据库需要同步设置索引值
     * @param
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/{path}/doSeckill")
    @ResponseBody
    public RespBean doSeckill(@PathVariable String path, User user, Long goodsId){
//        System.out.println(user);
        if(user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 判断是否存在uuid，根据这个来判断是否进行下面的秒杀
        boolean check = orderService.checkPath(user, goodsId, path);
        if (!check) {
            return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
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
        // 判断是否重复抢购
        String seckillOrder = (String) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if(StringUtils.hasLength(seckillOrder)){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        // 内存标记，减少redis的访问，标记是否还有库存，可不可以抢购
        if(EmptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 预减库存
        /* 不在采用该方法
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
         */
        Long stock = (Long) redisTemplate.execute(script, Collections.singletonList("seckillGoods:" + goodsId));
        if(stock < 0){
            EmptyStockMap.put(goodsId, true);
            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        // 接入mq，消息消费
        // 请求入队，立即返回排队中
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return RespBean.success(0);
        //return RespBean.error(RespBeanEnum.SESSION_ERROR);
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
    @ResponseBody
    public RespBean getResult(User user, Long goodsId){
        if(user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        // 返回订单数
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void verifyCode(User user, @RequestParam Long goodsId, HttpServletResponse response){
        System.out.println("out goodsId");
        System.out.println(goodsId);
        if(user == null || goodsId < 0){
            throw new GlobalException(RespBeanEnum.REQUEST_ILLEGAL);
        }
    //    设置请求头为输出图片的类型
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 生成验证码，将结果存入redis
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 32, 3);
        redisTemplate.opsForValue().set("captcha:" + user.getId() + ":" + goodsId, captcha.text(), 300, TimeUnit.SECONDS);
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.error("验证码生成失败", e.getMessage());
        }
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

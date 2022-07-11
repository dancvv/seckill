package com.xxxxx.seckill.controller;

import com.xxxxx.seckill.entity.Goods;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.service.IGoodsService;
import com.xxxxx.seckill.service.IUserService;
import com.xxxxx.seckill.vo.DetailVo;
import com.xxxxx.seckill.vo.GoodsVo;
import com.xxxxx.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Classname Controller
 * @Description
 * @Version 1.0.0
 * @Date 2022/4/29 2:15 AM
 * @Created by weivang
 */

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    /**
     * 方法描述: 跳转商品页面
     * @since: 1.0
     * @param: [session, model, ticket]
     * @return: java.lang.String
     * @author: vang
     * @date: 2022/4/29
     */
    @RequestMapping("toList2")
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket){
//        通过session获取用户，并判断是否存在
        if(!StringUtils.hasLength(ticket)){
            return "login";
        }
//        System.out.println(session);
//        User user = (User) session.getAttribute(ticket);
        User user = userService.getUserByCookie(ticket, request, response);
        if(null == user) return "login";
        model.addAttribute("user", user);
        return "goodsList";
    }
    /**
     * 方法描述: 修改登录页面,去除参数传入，直接使用参数校验
     * mac  优化前QPS：547.7
     *      缓存QPS：502.6
     * @since: 1.0
     * @param:
     * @return:
     * @author: vang
     * @date: 2022/5/1
     */
    @RequestMapping(value = "toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model, User user){
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        Redis中获取页面，如果不为空，直接返回页面
        String html = (String) valueOperations.get("goodsList");
        if(StringUtils.hasLength(html)){
            return html;
        }
        model.addAttribute("user", user);
//        System.out.println(goodsService.findGoodsVo());
        model.addAttribute("goodsList", goodsService.findGoodsVo());
//        return "goodsList";
//        如果为空，则代表redis中没有这个页面，把数据存入redis中，然后返回
        WebContext context = new WebContext(request, response,request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if(StringUtils.hasLength(html)){
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }
    @GetMapping("/all")
    @ResponseBody
    public List<GoodsVo> all(){
        List<GoodsVo> goodsVo = goodsService.findGoodsVo();
        return goodsVo;
    }
    @RequestMapping(value = "/toDetail2/{goodsId}", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String findGoodsVoByGoodsId(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable Long goodsId){
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        Redis中获取页面，如果不为空，直接返回页面，redis用于缓存数据
        String html = (String) valueOperations.get("goodsDetail:"+goodsId);
        if(StringUtils.hasLength(html)){
            return html;
        }
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        int secKillStatus = 0;
        int remainSeconds;
//        判断秒杀逻辑
//        秒杀开始，当前时间超前开始时间
        if(nowDate.before(startDate)){
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
//            秒杀结束，当前时间落后于结束时间
        }else if (nowDate.after(endDate)){
            secKillStatus = 2;
            remainSeconds = -1;
//            秒杀进行中
        }else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("seckillStatus", secKillStatus);
        model.addAttribute("goods", goodsVo);
//        return "goodsDetail";
//        如果为空，手动渲染，存入redis并返回
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        if(StringUtils.hasLength(html)){
            valueOperations.set("goodsDetail:"+goodsId, html, 60, TimeUnit.SECONDS);
        }
        return html;
    }

    /**
     * 跳转商品详情页
     * @param request
     * @param response
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId){
//        查询用户，根据id
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date nowDate = new Date();
//        秒杀状态
        int secKillStatus = 0;
//        秒杀开始时间
        int remainSeconds ;
//        秒杀还未开始
        if (nowDate.before(startDate)){
            remainSeconds = (int)((startDate.getTime() - nowDate.getTime()) / 1000);
//         秒杀已结束
        }else if (nowDate.after(endDate)){
            secKillStatus = 2;
            remainSeconds = -1;
//            秒杀中
        }else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
//        创建订单流水表，使用一个vo来接收参数
        DetailVo detailVo = new DetailVo();
        detailVo.setGoodsVo(goods);
        detailVo.setUser(user);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(secKillStatus);
//        将vo的参数存入返回类中
        return RespBean.success(detailVo);
    }
}

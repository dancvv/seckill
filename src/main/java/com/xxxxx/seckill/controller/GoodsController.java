package com.xxxxx.seckill.controller;

import com.xxxxx.seckill.entity.Goods;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.service.IGoodsService;
import com.xxxxx.seckill.service.IUserService;
import com.xxxxx.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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
     * @since: 1.0
     * @param:
     * @return:
     * @author: vang
     * @date: 2022/5/1
     */
    @RequestMapping("toList")
    public String toLogin(Model model, User user){
        model.addAttribute("user", user);
        System.out.println(goodsService.findGoodsVo());
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }
    @GetMapping("/all")
    @ResponseBody
    public List<GoodsVo> all(){
        List<GoodsVo> goodsVo = goodsService.findGoodsVo();
        return goodsVo;
    }
    @RequestMapping("/toDetail/{goodsId}")
    public String findGoodsVoByGoodsId(Model model, User user, @PathVariable Long goodsId){
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
        return "goodsDetail";
    }
}

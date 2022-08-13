package com.xxxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxxx.seckill.entity.Order;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.vo.GoodsVo;
import com.xxxxx.seckill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2022-05-11
 */
public interface IOrderService extends IService<Order> {
    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    Order seckill(User user, GoodsVo goods);

    /**
     * 方法描述: 订单详情
     * @since: 1.0
     * @param: []
     * @return: com.xxxxx.seckill.vo.OrderDetailVo
     * @author: vang
     * @date: 2022/7/18
     */
    OrderDetailVo detail(Long orderId);

    String createPath(User user, Long goodsId);

    /*
     * 方法描述: 校验秒杀地址
     * @since: 1.0
     * @param: [user, goodsId]
     * @return: boolean
     * @author: weivang
     * @date: 2022/8/7
     */
    boolean checkPath(User user, Long goodsId, String path);

    /*
     * 方法描述: 校验验证码
     * @since: 1.0
     * @param: [user, goodsId, captcha]
     * @return: java.lang.Boolean
     * @author: weivang
     * @date: 2022/8/13
     */
    Boolean checkCaptcha(User user, Long goodsId, String captcha);
}

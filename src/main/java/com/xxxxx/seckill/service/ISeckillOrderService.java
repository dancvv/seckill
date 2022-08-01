package com.xxxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxxx.seckill.entity.SeckillOrder;
import com.xxxxx.seckill.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2022-05-11
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {
    /*
     * 方法描述: 获取秒杀结果
     * @since: 1.0
     * @param: [user, goodsId]
     * @return: java.lang.Long
     * @author: weivang
     * @date: 2022/7/30
     */
    Long getResult(User user, Long goodsId);

}

package com.xxxxx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxxx.seckill.entity.SeckillOrder;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.mapper.SeckillOrderMapper;
import com.xxxxx.seckill.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-05-11
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /*
     * 方法描述: 服务实现类，获取秒杀结果
     * @since: 1.0
     * @param: [user, goodsId]
     * @return: java.lang.Long
     * @author: weivang
     * @date: 2022/8/1
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if(null != seckillOrder){
            return seckillOrder.getId();
        }else {
            // 判断redis中是否还有这个订单
            if(redisTemplate.hasKey("isStockEmpty:" + goodsId)){
                return -1L;
            }else {
                return 0L;
            }
        }
    }
}

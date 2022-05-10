package com.xxxxx.seckill.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxxx.seckill.entity.Goods;
import com.xxxxx.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2022-05-11
 */
public interface IGoodsService extends IService<Goods> {


    /**
     * 查找所有商品
     * @return
     */
    List<GoodsVo> findGoodsVo();
}

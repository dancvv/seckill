package com.xxxxx.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxxx.seckill.entity.Goods;
import com.xxxxx.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2022-05-11
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}

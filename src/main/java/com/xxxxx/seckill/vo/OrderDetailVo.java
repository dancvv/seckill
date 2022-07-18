package com.xxxxx.seckill.vo;

import com.xxxxx.seckill.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname OrderDetailVo
 * @Description 订单返回对象
 * @Version 1.0.0
 * @Date 2022/7/18 11:38 PM
 * @Created by weivang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}

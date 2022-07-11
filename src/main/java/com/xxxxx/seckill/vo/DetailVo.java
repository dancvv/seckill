package com.xxxxx.seckill.vo;

import com.xxxxx.seckill.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 方法描述: 订单详情返回对象
 * @since: 1.0
 * @param:
 * @return:
 * @author: vang
 * @date: 2022/7/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {
    private User user;
    private GoodsVo goodsVo;
    private int secKillStatus;
    private int remainSeconds;
}

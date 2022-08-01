package com.xxxxx.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname SeckillMessage
 * @Description
 * @Version 1.0.0
 * @Date 2022/7/30 8:42 PM
 * @Created by weivang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {
    private User user;
    private Long goodsId;
}

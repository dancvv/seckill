package com.xxxxx.seckill.vo;


import lombok.*;

/**
 * @Author: wang
 * @Description: TO-DO
 * @Date: 2022/04/25/2:12 AM
 */

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

//    枚举类生成稍显怪异
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常");

    private final Integer code;
    private final String message;
}

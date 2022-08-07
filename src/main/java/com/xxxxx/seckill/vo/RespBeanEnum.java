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

//    通用

//    枚举类生成稍显怪异
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),
//    登录模块
    LOGIN_ERROR(500210, "用户名或密码错误"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212, "参数校验异常"),
//    秒杀模块
    EMPTY_STOCK(500500,"库存不足"),
    REPEATE_ERROR(500501, "该商品每人限购一件"),

    MOBILE_NOT_EXIST(500213, "手机号码不存在"),

    PASSWORD_UPDATE_FAIL(500214, "密码更新失败"),
    SESSION_ERROR(500215,"用户不存在"),
    ORDER_NOT_EXIST(500216,"订单不存在"),
    REQUEST_ILLEGAL(500502, "请求非法，请重新尝试");
    private final Integer code;
    private final String message;
}

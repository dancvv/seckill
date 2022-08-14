package com.xxxxx.seckill.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname AccessLimit
 * @Description 注解设置，运行时，作用在方法上
 * @Version 1.0.0
 * @Date 2022/8/14 9:20 PM
 * @Created by weivang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int second();
    int maxCount();
    /* 默认需要登录 */
    boolean needLogin() default true;
}

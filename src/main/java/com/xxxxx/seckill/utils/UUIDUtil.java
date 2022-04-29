package com.xxxxx.seckill.utils;


import java.util.UUID;

/**
 * @Classname UUIDUtil
 * @Description cookie生成
 * @Version 1.0.0
 * @Date 2022/4/29 1:55 AM
 * @Created by weivang
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

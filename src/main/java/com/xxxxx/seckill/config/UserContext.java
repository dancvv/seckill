package com.xxxxx.seckill.config;

import com.xxxxx.seckill.entity.User;

/**
 * @Classname UserContext
 * @Description ThreadLocal实战
 * @Version 1.0.0
 * @Date 2022/8/14 10:03 PM
 * @Created by weivang
 */
public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();
    public static void setUser(User user){
        userHolder.set(user);
    }
    public static User getUser(){
        return userHolder.get();
    }
}

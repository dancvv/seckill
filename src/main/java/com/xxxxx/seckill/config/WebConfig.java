package com.xxxxx.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Classname WebConfig
 * @Description
 * @Version 1.0.0
 * @Date 2022/5/1 2:01 AM
 * @Created by weivang
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private UserArgumentResolver userArgumentResolver;
//    自定义解析器
//    WebMvcCOnfigurer默认拦截静态资源
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(userArgumentResolver);
    }

}

package com.xxxxx.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Classname RedisConfig
 * @Description redis配置类
 * @Version 1.0.0
 * @Date 2022/4/30 10:12 AM
 * @Created by weivang
 */
@Configuration
public class RedisConfig {
    /**
     * 方法描述: redis序列化
     * @since: 1.0
     * @param:
     * @return:
     * @author: vang
     * @date: 2022/4/30
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        value值的序列化，redis默认使用jdk序列化，二进制的，比较长
//        Jackson序列化，产生的是java对象，需要传入对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        hash类型 key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        hash类型 value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());


//        注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}

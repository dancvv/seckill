package com.xxxxx.seckill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisScript<Long> script;
    @Test
    void testLock01() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 占位，如果key不存在才可以设置成功
        // 给锁添加一个过期时间，防止应用在运行过程中抛出异常导致锁无法正常释放
        Boolean aBoolean = valueOperations.setIfAbsent("k1", "v1");
        // 如果占位成功
        if(aBoolean){
            valueOperations.set("name", "xxxxx");
            String str = (String) valueOperations.get("name");
            System.out.println("占锁成功"+str);
            Integer.parseInt("k1");
            // 操作结束，删除锁
            redisTemplate.delete("k1");
        }else{
            System.out.println("有线程正在使用，请稍后再试");
        }
    }

    @Test
    public void testLock02(){
        // 第一个
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Boolean aBoolean = valueOperations.setIfAbsent("k1", "v1", 5, TimeUnit.SECONDS);
        if(aBoolean){
            valueOperations.set("name", "xxxxx");
            String name = (String) valueOperations.get("name");
            Integer.parseInt("xxx");
            redisTemplate.delete("k1");
        }else {
            System.out.println("有线程在使用，请稍后再试");
        }
    }

    @Test
    public void testLock03(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String value = UUID.randomUUID().toString();
        Boolean isLock = valueOperations.setIfAbsent("k1", value, 5, TimeUnit.MINUTES);
        if(isLock){
            valueOperations.set("name", "xxxx");
            String name = (String) valueOperations.get("name");
            System.out.println("name = " + name);
            System.out.println(valueOperations.get("k1"));
            Boolean result = (Boolean) redisTemplate.execute(script, Collections.singletonList("k1"), value);
            System.out.println(result);
        }else{
            System.out.println("有线程在使用，请稍后");
        }
    }
}

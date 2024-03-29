package com.xxxxx.seckill.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @Classname JsonUtil
 * @Description
 * @Version 1.0.0
 * @Date 2022/7/30 9:31 PM
 * @Created by weivang
 */
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /** * 将对象转换成json字符串 * * @param obj * @return */
    public static String object2JsonStr(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
//打印异常信息
            e.printStackTrace();
        }

        return null;
    }

    /** * 将字符串转换为对象 * * @param <T> 泛型 */
    public static <T> T jsonStr2Object(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr.getBytes("UTF-8"), clazz);
        } catch (JsonParseException e) {
            e.printStackTrace(); }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static <T> List<T> jsonToList(String jsonStr, Class<T> beanType) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = objectMapper.readValue(jsonStr, javaType); return list;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}

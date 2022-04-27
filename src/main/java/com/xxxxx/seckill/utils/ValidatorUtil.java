package com.xxxxx.seckill.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: wang
 * @Description: TO-DO手机号校验
 * @Date: 2022/04/28/1:16 AM
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("[1][3-9][0-9]{9}$");
    public static boolean isMobile(String mobile){
        if(!StringUtils.hasLength(mobile)){
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}

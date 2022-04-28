package com.xxxxx.seckill.exception;

import com.xxxxx.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname GlobalException
 * @Description 异常处理
 * @Version 1.0.0
 * @Date 2022/4/29 12:29 AM
 * @Created by weivang
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException {
    private RespBeanEnum respBeanEnum;

}

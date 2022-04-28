package com.xxxxx.seckill.exception;

import com.xxxxx.seckill.vo.RespBean;
import com.xxxxx.seckill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Classname GlobalExceptionHandler
 * @Description 全局处理
 * @Version 1.0.0
 * @Date 2022/4/29 12:30 AM
 * @Created by weivang
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

//    异常处理
    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if(e instanceof BindException){
            BindException ex = (BindException) e;
            RespBean error = RespBean.error(RespBeanEnum.BIND_ERROR);
            error.setMessage("参数校验异常："+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return error;
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}

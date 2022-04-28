package com.xxxxx.seckill.vo;

import com.xxxxx.seckill.utils.ValidatorUtil;
import com.xxxxx.seckill.validator.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: wang
 * @Description: 参数校验，自定义规则
 * @Date: 2022/04/28/11:56 PM
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
//        初始化，定义类型是否可以获取
        required = constraintAnnotation.required();
//        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//        校验规则
        if(required){
            return ValidatorUtil.isMobile(s);
        }else {
            if(!StringUtils.hasLength(s)){
                return true;
            }else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}

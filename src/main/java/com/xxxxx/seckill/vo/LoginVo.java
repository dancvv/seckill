package com.xxxxx.seckill.vo;

import com.xxxxx.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author: wang
 * @Description: TO-DO
 * @Date: 2022/04/28/12:42 AM
 */

@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(min = 32)
    private String password;

}

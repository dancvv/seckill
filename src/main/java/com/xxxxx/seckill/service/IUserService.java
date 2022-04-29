package com.xxxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.vo.LoginVo;
import com.xxxxx.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2022-04-25
 */
public interface IUserService extends IService<User> {

    /**
     * 方法描述: 登录
     * @since: 1.0
     * @param: [loginVo]
     * @return: com.xxxxx.seckill.vo.RespBean
     * @author: vang
     * @date: 2022/4/28
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
}

package com.xxxxx.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.exception.GlobalException;
import com.xxxxx.seckill.mapper.UserMapper;
import com.xxxxx.seckill.service.IUserService;
import com.xxxxx.seckill.utils.CookieUtil;
import com.xxxxx.seckill.utils.MD5Util;
import com.xxxxx.seckill.utils.UUIDUtil;
import com.xxxxx.seckill.vo.LoginVo;
import com.xxxxx.seckill.vo.RespBean;
import com.xxxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-04-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    private UserMapper userMapper;
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
////        参数校验
//        if(!StringUtils.hasLength(mobile) || StringUtils.hasLength(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if(!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
//        根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if(null == user){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
//        判断密码是否正确
//        System.out.println((MD5Util.fromPassToDBPass(password, user.getSlat())));
//        校验方式是通过用户输入的密码，与数据库中的密码进行比对
//        password为用户传入的，user为数据库的数据
        if( !MD5Util.fromPassToDBPass(password, user.getSlat()).equals(user.getPassword()) ){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
//        生成cookie
        String ticket = UUIDUtil.uuid();
        request.getSession().setAttribute(ticket, user);
//        设置cookie值
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success();
    }
}

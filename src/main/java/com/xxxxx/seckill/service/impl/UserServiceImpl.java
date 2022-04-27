package com.xxxxx.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.mapper.UserMapper;
import com.xxxxx.seckill.service.IUserService;
import com.xxxxx.seckill.utils.MD5Util;
import com.xxxxx.seckill.utils.ValidatorUtil;
import com.xxxxx.seckill.vo.LoginVo;
import com.xxxxx.seckill.vo.RespBean;
import com.xxxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if(!StringUtils.hasLength(mobile) || StringUtils.hasLength(password)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }
        User user = userMapper.selectById(mobile);
        if(user == null){
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }
//        判断密码是否正确
        if(MD5Util.fromPassToDBPass(password, user.getSlat()).equals(user.getPassword())){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        return null;
    }
}

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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private RedisTemplate redisTemplate;
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
        System.out.println(user);
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
//        request.getSession().setAttribute(ticket, user);
//        将用户信息存入redis中
        redisTemplate.opsForValue().set("user:" + ticket, user);
//        设置cookie值
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success(ticket);
    }

    /**
     * 方法描述: 根据cookie获取用户
     * @since: 1.0
     * @param:
     * @return:
     * @author: vang
     * @date: 2022/4/30
     */
    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
//    public User getUserByCookie(String userTicket) {
//        判断对象是否为空
        if(!StringUtils.hasLength(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if(user != null){
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }

    @Override
    public User getUsers(LoginVo loginVo) {
        User user = userMapper.selectById(loginVo.getMobile());
        return user;
    }

    /**
     * 更新密码
     * @param userTicket
     * @param id
     * @param password
     * @return
     */
    @Override
    public RespBean updatePassword(String userTicket, Long id, String password) {
        User user = userMapper.selectById(id);
        if(user == null){
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSlat()));
        int result = userMapper.updateById(user);
        if(1 == result){
//            删除redis
//            这里只删除，不进行数据读取
            redisTemplate.delete("user:"+ userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }
}

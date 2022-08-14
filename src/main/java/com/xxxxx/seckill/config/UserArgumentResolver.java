package com.xxxxx.seckill.config;

import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.service.IUserService;
import com.xxxxx.seckill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname UserArgumentResolver
 * @Description
 * @Version 1.0.0
 * @Date 2022/5/1 2:04 AM
 * @Created by weivang
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private IUserService userService;
//    条件判断
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
//        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//        String ticket = CookieUtil.getCookieValue(request, "userTicket");
//        if(!StringUtils.hasLength(ticket)){
//            return null;
//        }
////        return null;
////        redis 缓存用户
//        return userService.getUserByCookie(ticket, request, response);
//        return null;
        return UserContext.getUser();
    }
}

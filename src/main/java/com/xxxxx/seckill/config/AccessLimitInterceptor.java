package com.xxxxx.seckill.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.service.IUserService;
import com.xxxxx.seckill.utils.CookieUtil;
import com.xxxxx.seckill.vo.RespBean;
import com.xxxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Classname AccessLimitInterceptor
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/14 9:25 PM
 * @Created by weivang
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    /*
     * 方法描述: 重写方法，拦截处理之前的操作
     * @since: 1.0
     * @param: [request, response, handler]
     * @return: boolean
     * @author: weivang
     * @date: 2022/8/14
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            User user = getUser(request, response);
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){
                return true;
            }
            int second = accessLimit.second();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if(needLogin){
                if(user == null){
                    render(response, RespBeanEnum.SESSION_ERROR);
                    return false;
                }
                key += ":" + user.getId();
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer count = (Integer) valueOperations.get(key);
            if(count == null) {
                valueOperations.set(key, 1, second, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                valueOperations.increment(key);
            }else{
                render(response, RespBeanEnum.ACCESS_LIMIT_REACHED);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /*
     * 方法描述: 构建返回对象
     * @since: 1.0
     * @param: [response, sessionError]
     * @return: void
     * @author: weivang
     * @date: 2022/8/14
     */
    private void render(HttpServletResponse response, RespBeanEnum respBeanEnum) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        RespBean respBean = RespBean.error(respBeanEnum);
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
    }

    /*
     * 方法描述: 获取当前用户
     * @since: 1.0
     * @param: [request, response]
     * @return: com.xxxxx.seckill.entity.User
     * @author: weivang
     * @date: 2022/8/14
     */
    private User getUser(HttpServletRequest request, HttpServletResponse response) {

        String ticket = CookieUtil.getCookieValue(request, "userTicket");
        if(!StringUtils.hasLength(ticket)){
            return null;
        }
//        return null;
//        redis 缓存用户
        return userService.getUserByCookie(ticket, request, response);
    }
}

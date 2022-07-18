package com.xxxxx.seckill.controller;

import com.xxxxx.seckill.entity.User;
import com.xxxxx.seckill.service.IOrderService;
import com.xxxxx.seckill.vo.OrderDetailVo;
import com.xxxxx.seckill.vo.RespBean;
import com.xxxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wang
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService iOrderService;

    /**
     * 方法描述: 订单详情，根据传入orderId选择
     * @since: 1.0
     * @param: [user, orderId]
     * @return: com.xxxxx.seckill.vo.RespBean
     * @author: vang
     * @date: 2022/7/19
     */
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId){
        if(null == user){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detailVo = iOrderService.detail(orderId);
        return RespBean.success(detailVo);
    }

}

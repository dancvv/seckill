package com.xxxxx.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wang
 * @Description: TO-DO
 * @Date: 2022/04/25/2:12 AM
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    /**
     * 方法描述: 成功返回描述结果
     * @since: 1.0
     * @param: []
     * @return: com.xxxxx.seckill.vo.RespBean
     * @author: vang
     * @date: 2022/4/25
     */
    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),null);
    }

    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(),obj);
    }

    /**
     * 方法描述: 失败返回结果
     * @since: 1.0
     * @param: [respBeanEnum]
     * @return: com.xxxxx.seckill.vo.RespBean
     * @author: vang
     * @date: 2022/4/25
     */
    public static RespBean error(RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    public static RespBean error(RespBeanEnum respBeanEnum, Object obj){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }
}

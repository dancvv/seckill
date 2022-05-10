package com.xxxxx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxxx.seckill.entity.Order;
import com.xxxxx.seckill.mapper.OrderMapper;
import com.xxxxx.seckill.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-05-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public boolean saveBatch(Collection<Order> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Order> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<Order> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Order entity) {
        return false;
    }

    @Override
    public Order getOne(Wrapper<Order> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Order> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<Order> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }
}

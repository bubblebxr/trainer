package com.example.se_project.service.Impl;

import com.example.se_project.entity.Order;
import com.example.se_project.mapper.IOrderMapper;
import com.example.se_project.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    IOrderMapper orderMapper;

    @Override
    public Order getOrderByOidAndUid(String oid, String uid) {
        return orderMapper.getOrderByOidAndUid(oid, uid);
    }

    @Override
    public void addOrder(Order order) {
        orderMapper.addOrder(order);
    }

    @Override
    public void cancelOrder(Order order) {
        orderMapper.cancelOrder(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderMapper.deleteOrder(order);
    }
}

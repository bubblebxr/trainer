package com.example.se_project.service.Impl;

import com.example.se_project.entity.Order;
import com.example.se_project.mapper.IOrderMapper;
import com.example.se_project.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    IOrderMapper orderMapper;

    @Override
    public List<Order> getOrdersByUidAndStatus(String uid, Order.OrderStatus status, Order.OrderType type) {
        return orderMapper.getOrdersByUidAndStatus(uid, status, type);
    }

    @Override
    public List<Order> getOrderByUid(String uid, Order.OrderType type) {
        return orderMapper.getOrderByUid(uid, type);
    }

    @Override
    public Order getOrderByOidAndUid(String oid, String uid) {
        return orderMapper.getOrderByOidAndUid(oid, uid);
    }

    @Override
    public Order getOrder(String oid) {
        return orderMapper.getOrder(oid);
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

    @Override
    public void setCancelTime(String oid, String cancelTime) {
        orderMapper.setCancelTime(oid, cancelTime);
    }

    @Override
    public List<Map<String,String>> getIdByUidAndStatus(String userID, String status) {
        return orderMapper.getIdByUidAndStatus(userID, status);
    }

    @Override
    public List<Map<String,String>> getIdByUid(String userID) {
        return orderMapper.getIdByUid(userID);
    }
}

package com.example.se_project.service;

import com.example.se_project.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface IOrderService {

    public List<Order> getOrdersByUidAndStatus(String uid, Order.OrderStatus status, Order.OrderType type);

    public List<Order> getOrderByUid(String uid, Order.OrderType type);

    public Order getOrderByOidAndUid(String oid, String uid);

    public Order getOrder(String oid);

    public void addOrder(Order order);

    public void cancelOrder(Order order);

    public void deleteOrder(Order order);

    void setCancelTime(String oid,String cancelTime);
    List<Map<String,String>> getIdByUid(String userID);
    List<Map<String,String>> getIdByUidAndStatus(String userID,String status);
}

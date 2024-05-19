package com.example.se_project.service;

import com.example.se_project.entity.Order;
import org.springframework.stereotype.Component;

@Component
public interface IOrderService {
    public Order getOrderByOidAndUid(String oid, String uid);

    public void addOrder(Order order);

    public void cancelOrder(Order order);

    public void deleteOrder(Order order);
}

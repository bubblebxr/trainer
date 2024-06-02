package com.example.se_project.service.Impl;

import com.example.se_project.entity.Food;
import com.example.se_project.entity.FoodOrder;
import com.example.se_project.entity.Order;
import com.example.se_project.mapper.IFoodMapper;
import com.example.se_project.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("foodService")
public class FoodServiceImpl implements IFoodService {
    @Autowired
    IFoodMapper foodMapper;

    @Override
    public List<Food> getAllFood(String trainId, String date, String type) {
        return foodMapper.getAllFood(trainId, date, type);
    }

    @Override
    public Food findFoodByAllKeys(String trainId, String date, String type, String name) {
        return foodMapper.findFoodByAllKeys(trainId, date, type, name);
    }


    @Override
    public void addFoodOrder(FoodOrder foodOrder) {
        foodMapper.addFoodOrder(foodOrder);
    }


    @Override
    public List<FoodOrder> getFoodOrdersByOid(String oid) {
        return foodMapper.getFoodOrdersByOid(oid);
    }

    @Override
    public List<FoodOrder> getFoodOrdersByTrain(String trainId, String trainDate) {
        return foodMapper.getFoodOrdersByTrain(trainId, trainDate);
    }

    @Override
    public List<Order> getTrainRelatedFoodOrders(String tid, String date, String uid) {
        return foodMapper.getTrainRelatedFoodOrders(tid, date, uid);
    }


    @Override
    public void reduceFoodNum(Food food, int n) {
        foodMapper.reduceFoodNum(food, n);
    }


}

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
    public List<Food> getAllFood(String trainId, String date, String type){
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
    public List<FoodOrder> getFoodOrdersByTrain(String tid, String date) {
        return foodMapper.getFoodOrdersByTrain(tid, date);
    }


}

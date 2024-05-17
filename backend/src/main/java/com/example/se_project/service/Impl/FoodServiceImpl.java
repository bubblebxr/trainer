package com.example.se_project.service.Impl;

import com.example.se_project.entity.Food;
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
}

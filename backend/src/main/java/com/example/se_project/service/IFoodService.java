package com.example.se_project.service;

import com.example.se_project.entity.Food;
import com.example.se_project.entity.FoodOrder;
import com.example.se_project.entity.Order;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IFoodService {
    public List<Food> getAllFood(String trainId, String date, String type);

    public Food findFoodByAllKeys(String trainId, String date, String type, String name);

    public void addFoodOrder(FoodOrder foodOrder);

    public List<FoodOrder> getFoodOrdersByOid(String oid);
}

package com.example.se_project.controller;

import com.example.se_project.entity.Food;
import com.example.se_project.service.IFoodService;
import com.example.se_project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FoodController {
    @Autowired
    private IFoodService foodService;

    Map<String, Object> getAllFood(@RequestBody Map<String, String> requestMap) {
        String trainId = requestMap.get("tid");
        String date = requestMap.get("date");
        String type = requestMap.get("time");
        List<Food> foodList = foodService.getAllFood(trainId, date, type);
        List<Map<String, Object>> resultList = foodList.stream()
                .map(food -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", food.getName());
                    map.put("price", food.getPrice());
                    map.put("photo",food.getImage());
                    map.put("num",food.getNum());
                    return map;
                })
                .collect(Collectors.toList());
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("result", resultList);
        return resMap;
    }
}

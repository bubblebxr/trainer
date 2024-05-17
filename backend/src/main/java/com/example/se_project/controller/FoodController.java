package com.example.se_project.controller;

import com.example.se_project.entity.Food;
import com.example.se_project.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FoodController {
    @Autowired
    private IFoodService foodService;
    @Autowired
    private Food food;

    @GetMapping("/food/{tid}/{date}/{time}")
    Map<String, List<Food>> getAllFood(@PathVariable String tid, @PathVariable String date, @PathVariable String time) {
        List<Food> foodList = foodService.getAllFood(tid, date, time);
        Map<String, List<Food>> resMap = new HashMap<>();
        resMap.put("result", foodList);
        return resMap;
    }
}

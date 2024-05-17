package com.example.se_project.service;

import com.example.se_project.entity.Food;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IFoodService {
    public List<Food> getAllFood(String trainId, String date, String type);
}

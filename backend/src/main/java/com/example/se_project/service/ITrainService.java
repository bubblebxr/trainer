package com.example.se_project.service;

import com.example.se_project.entity.Train;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ITrainService {
    public List<Train> searchTrain(String start_station, String arrive_station, String date, Integer isGD, Integer sort_type, Boolean[] seat_type, Boolean isHide);
}

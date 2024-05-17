package com.example.se_project.service.Impl;

import com.example.se_project.entity.Stopover;
import com.example.se_project.entity.Train;
import com.example.se_project.mapper.ITrainMapper;
import com.example.se_project.service.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("trainService")
public class TrainServiceImpl implements ITrainService {
    @Autowired
    private ITrainMapper trainMapper;
    @Override
    public List<Train> searchTrain(String start_station, String arrive_station, String date, Integer isGD, Integer sort_type, List<Boolean> seat_type, Boolean isHide){
        System.out.println(start_station + arrive_station +  date+"isGd"+isGD+seat_type);
        return trainMapper.searchTrain(start_station,arrive_station,date,isGD,sort_type,seat_type,isHide);
    }

    @Override
    public List<Stopover> searchStopover(String trainId) {
        return trainMapper.searchStopover(trainId);
    }
}

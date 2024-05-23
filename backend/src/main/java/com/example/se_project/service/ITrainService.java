package com.example.se_project.service;

import com.example.se_project.entity.Stopover;
import com.example.se_project.entity.Train;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ITrainService {
    public List<Train> searchTrain(String start_station, String arrive_station, String date, Integer isGD, Integer sort_type, List<Boolean> seat_type, Boolean isHide);

    public List<Stopover> searchStopover(String trainId);

    public void addTrainOrderDetail(String oid,String trainId, String trainDate, String name, String identification, String seatType);
}

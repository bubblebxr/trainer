package com.example.se_project.service.Impl;

import com.example.se_project.entity.Stopover;
import com.example.se_project.entity.Train;
import com.example.se_project.entity.TrainOrder;
import com.example.se_project.mapper.ITrainMapper;
import com.example.se_project.service.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("trainService")
public class TrainServiceImpl implements ITrainService {
    @Autowired
    private ITrainMapper trainMapper;

    @Override
    public List<Train> searchTrain(String start_station, String arrive_station, String date, Integer isGD, Integer sort_type, List<Boolean> seat_type, Boolean isHide) {
        return trainMapper.searchTrain(start_station, arrive_station, date, isGD, sort_type, seat_type, isHide);
    }

    @Override
    public Train getTrainByTidAndDate(String tid, String date) {
        return trainMapper.getTrainByTidAndDate(tid, date);
    }

    @Override
    public List<Stopover> searchStopover(String trainId) {
        return trainMapper.searchStopover(trainId);
    }

    @Override
    public List<TrainOrder> getTrainOrdersByOid(String oid) {
        return trainMapper.getTrainOrdersByOid(oid);
    }

    @Override
    public TrainOrder getTrainOrdersByIdentificationAndDate(String identification, String date) {
        return trainMapper.getTrainOrdersByIdentificationAndDate(identification, date);
    }


    @Override
    public void addTrainOrderDetail(String oid, String trainId, String trainDate, String name, String identification, String seatType) {
        trainMapper.addTrainOrderDetail(oid, trainId, trainDate, name, identification, seatType);
    }

    @Override
    public Map<String, Object> getSelfOrderDetail(String oid, String userID) {
        return trainMapper.getSelfOrderDetail(oid, userID);
    }

    @Override
    public Map<String, Object> getTrainByIdAndDate(String trainId, String trainDate) {
        return trainMapper.getTrainByIdAndDate(trainId, trainDate);
    }

    @Override
    public String getStartTime(String trainId, String trainDate) {
        return trainMapper.getStartTime(trainId, trainDate);
    }

    @Override
    public List<Map<String, Object>> getTrainIdAndDate(String orderId) {
        return trainMapper.getTrainIdAndDate(orderId);
    }
    @Override
    public void updateTrainSeat(String trainId, String trainDate, Integer num1, Integer num2, Integer num3, Integer num4, Integer num5, Integer num6){
        trainMapper.updateTrainSeat(trainId,trainDate,num1,num2,num3,num4,num5,num6);
    }

}

package com.example.se_project.service.Impl;

import com.example.se_project.entity.Passenger;
import com.example.se_project.mapper.IPassengerMapper;
import com.example.se_project.service.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("passengerService")
public class PassengerServiceImpl implements IPassengerService {

    @Autowired
    private IPassengerMapper passengerMapper;

    @Override
    public boolean addPassenger(String name, String identification, String phone, String userId) {
        return passengerMapper.addPassenger(name, identification, phone, userId);
    }

//    @Override
//    public boolean updatePassengerName(String id, String oldidentification, String newname) {
//        return passengerMapper.updatePassengerName(id, oldidentification, newname);
//    }
//
//    @Override
//    public boolean updatePassengerIdentification(String id, String oldidentification, String newidentification) {
//        return passengerMapper.updatePassengerIdentification(id, oldidentification, newidentification);
//    }
//
//    @Override
//    public boolean updatePassengerPhone(String id, String oldidentification, String newphone) {
//        return passengerMapper.updatePassengerPhone(id, oldidentification, newphone);
//    }

    @Override
    public boolean updatePassenger(String id, String oldidentification, String newname, String newidentification, String newphone) {
        return passengerMapper.updatePassenger(id, oldidentification, newname, newidentification, newphone);
    }

    @Override
    public List<Passenger> showPassengerInfo(String userId) {
        return passengerMapper.showPassengerInfo(userId);
    }

    @Override
    public boolean deletePassenger(String userId, String name, String identification) {
        return passengerMapper.deletePassenger(userId, name, identification);
    }

    @Override
    public Passenger getPassengerByUidAndId(String id, String identification) {
        return passengerMapper.getPassengerByUidAndId(id, identification);
    }
}

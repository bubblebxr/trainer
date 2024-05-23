package com.example.se_project.service;

import com.example.se_project.entity.Passenger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IPassengerService {
    public boolean addPassenger(String name, String identification, String phone, String userId);

//     boolean updatePassengerName(String id, String oldidentification, String newname);
//
//    boolean updatePassengerIdentification(String id, String oldidentification, String newidentification);
//
//    boolean updatePassengerPhone(String id, String oldidentification, String newphone);

    boolean updatePassenger(String id, String oldidentification, String newname, String newidentification, String newphone);

    public List<Passenger> showPassengerInfo(String userId);

    public boolean deletePassenger(String id, String name, String identification);
Passenger getPassengerByUidAndId(String id, String identification);
}

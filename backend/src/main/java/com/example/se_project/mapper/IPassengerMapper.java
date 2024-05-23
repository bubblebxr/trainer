package com.example.se_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.se_project.entity.Passenger;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IPassengerMapper extends BaseMapper<Passenger> {
    boolean addPassenger(String name, String identification, String phone, String userId);

//    boolean updatePassengerName(String id, String oldidentification, String newname);
//
//    boolean updatePassengerIdentification(String id, String oldidentification, String newidentification);
//
//    boolean updatePassengerPhone(String id, String oldidentification, String newphone);

    boolean updatePassenger(String id, String oldidentification, String newname, String newidentification, String newphone);

    List<Passenger> showPassengerInfo(String userId);

    boolean deletePassenger(String userId, String name, String identification);

    Passenger getPassengerByUidAndId(String id, String identification);
}

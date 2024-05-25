package com.example.se_project.service.Impl;

import com.example.se_project.entity.Comment;
import com.example.se_project.entity.Hotel;
import com.example.se_project.entity.Room;
import com.example.se_project.mapper.IHotelMapper;
import com.example.se_project.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("hotelService")
public class HotelServiceImpl implements IHotelService {
    @Autowired
    IHotelMapper hotelMapper;

    // 根据city，找出所有该city的hotelId
    @Override
    public List<String> searchHotelByCity(String city) {
        return hotelMapper.searchHotelByCity(city);
    }


    @Override
    public List<Comment> searchComment(String id) {
        return hotelMapper.searchComment(id);
    }


    @Override
    public List<String> getPlaces() {
        return hotelMapper.getPlaces();
    }

    @Override
    public List<Map<String, Object>> getAvailableRoom(String id, String arrive_date, String ldeparture_date) {
        return hotelMapper.getAvailableRoom(id, arrive_date, ldeparture_date);
    }

    @Override
    public Integer getCommentNum(String id) {
        return hotelMapper.getCommentNum(id);
    }

    @Override
    public Hotel getHotelInfo(String id) {
        return hotelMapper.getHotelInfo(id);
    }

    @Override
    public List<String> getHotelPhoto(String id) {
        return hotelMapper.getHotelPhoto(id);
    }

    @Override
    public Double getHotelRank(String id) {
        return hotelMapper.getHotelRank(id);
    }

    @Override
    public List<Map<String, Object>> getRoomDetail(String id, String arrive_date, String ldeparture_date) {
        return hotelMapper.getRoomDetail(id, arrive_date, ldeparture_date);
    }

    @Override
    public void addHotelorderDetail(String id, String oid, String checkinTime, String checkoutTime, Integer roomNum, String roomType, String customerName, String customerId) {
        hotelMapper.addHotelorderDetail(id, oid, checkinTime, checkoutTime, roomNum, roomType, customerName, customerId);
    }

    @Override
    public Map<String, String> getHotelName(String id) {
        return hotelMapper.getHotelName(id);
    }

    @Override
    public List<Map<String, Object>> getHotelOrderDetail(String oid) {
        return hotelMapper.getHotelOrderDetail(oid);
    }

}

package com.example.se_project.service.Impl;

import com.example.se_project.controller.HotelController;
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

    @Override
    public List<Integer> searchAvailableHotelId(String city, String arrive_date, String Ideparture_date) {
        return hotelMapper.searchAvailableHotel(city, arrive_date, Ideparture_date);
    }

    @Override
    public Hotel searchHotel(Integer id) {
        return hotelMapper.searchHotel(id);
    }

    @Override
    public List<Room> searchRoom(Integer id) {
        return hotelMapper.searchRoom(id);
    }

    @Override
    public List<Comment> searchComment(Integer id) {
        return hotelMapper.searchComment(id);
    }

    @Override
    public void updateMin(Integer id, String miniprice) {
        hotelMapper.updatemin(id, miniprice);
    }
}

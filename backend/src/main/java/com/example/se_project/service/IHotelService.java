package com.example.se_project.service;

import com.example.se_project.entity.Comment;
import com.example.se_project.entity.Hotel;
import com.example.se_project.entity.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface IHotelService {
    public List<Integer> searchAvailableHotelId(String city, String arrive_date, String Ideparture_date);

    public Hotel searchHotel(Integer id);

    public List<Room> searchRoom(Integer id);

    public List<Comment> searchComment(Integer id);

    void updateMin(Integer id, String miniprice);

    List<String> getPlaces();
}

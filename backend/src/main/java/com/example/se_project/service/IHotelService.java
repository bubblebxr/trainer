package com.example.se_project.service;

import com.example.se_project.entity.Comment;
import com.example.se_project.entity.Hotel;
import com.example.se_project.entity.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface IHotelService {
    List<String> searchHotelByCity(String city);


    List<Comment> searchComment(String id);


    List<String> getPlaces();

    List<Map<String, Object>> getAvailableRoom(String id, String arrive_date, String ldeparture_date);

    Integer getCommentNum(String id);

    Hotel getHotelInfo(String id);

    List<String> getHotelPhoto(String id);

    Double getHotelRank(String id);

    List<Map<String, Object>> getRoomDetail(String id, String arrive_date, String ldeparture_date);
}

package com.example.se_project.mapper;

import com.example.se_project.controller.HotelController;
import com.example.se_project.entity.Comment;
import com.example.se_project.entity.Hotel;
import com.example.se_project.entity.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IHotelMapper {

    List<Comment> searchComment(String id);

    List<String> searchHotelByCity(String city);


    List<String> getPlaces();

    List<Map<String, Object>> getAvailableRoom(String id, String arrive_date, String ldeparture_date);

    Integer getCommentNum(String id);

    Hotel getHotelInfo(String id);

    List<String> getHotelPhoto(String id);

    Double getHotelRank(String id);

    List<Map<String, Object>> getRoomDetail(String id, String arrive_date, String ldeparture_date);
}

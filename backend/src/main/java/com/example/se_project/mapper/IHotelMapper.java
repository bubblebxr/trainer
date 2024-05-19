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
    Hotel searchHotel(Integer id);
    List<Comment> searchComment(Integer id);
    List<Room> searchRoom(Integer id);

    // 查询订购期间有可用房型的酒店编号
    List<Integer> searchAvailableHotel(String city, String arrive_date, String Ideparture_date);
    void updatemin(Integer id,String miniprice);

    List<String> getPlaces();
}

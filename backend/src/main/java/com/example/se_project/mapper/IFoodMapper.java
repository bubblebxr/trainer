package com.example.se_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.se_project.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IFoodMapper extends BaseMapper<Food> {
    @Select("select * from food where trainId=#{trainId} and date = #{date} and type = #{type}")
    List<Food> getAllFood(String trainId, String date, String type);
}

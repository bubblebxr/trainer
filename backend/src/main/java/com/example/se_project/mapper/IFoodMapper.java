package com.example.se_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.se_project.entity.Food;
import com.example.se_project.entity.FoodOrder;
import com.example.se_project.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IFoodMapper extends BaseMapper<Food> {
    @Select("select * from foods where trainId=#{trainId} and date = #{date} and type = #{type}")
    List<Food> getAllFood(String trainId, String date, String type);

    @Select("select * from foods where trainId=#{trainId} and date = #{date} and type = #{type} and name = #{name}")
    Food findFoodByAllKeys(String trainId, String date, String type, String name);

    void addFoodOrder(FoodOrder foodOrder);

    @Select("select * from foodOrders where oid = #{oid}")
    List<FoodOrder> getFoodOrdersByOid(String oid);

    @Select("select * from foodOrders where trainId = #{tid} and trainDate = #{date}")
    List<FoodOrder> getFoodOrdersByTrain(String tid, String date);

    @Update("update foods set num = num - #{num}")
    void reduceFoodNum(Food food, int num);
}

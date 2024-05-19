package com.example.se_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.se_project.entity.Food;
import com.example.se_project.entity.FoodOrder;
import com.example.se_project.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IFoodMapper extends BaseMapper<Food> {
    @Select("select * from foods where trainId=#{trainId} and date = #{date} and type = #{type}")
    List<Food> getAllFood(String trainId, String date, String type);

    @Select("select * from foods where trainId=#{trainId} and date = #{date} and type = #{type} and name = #{name}")
    Food findFoodByAllKeys(String trainId, String date, String type, String name);

    void addFoodOrder(FoodOrder foodOrder);

    @Select("select * from orders where uid = #{uid} and orderStatus = #{status} and orderType = 0")
    List<Order> getOrdersByUidAndStatus(String uid, Order.OrderStatus status);

    @Select("select * from orders where uid = #{uid}")
    List<Order> getOrderByUid(String uid);

    @Select("select * from foodOrders where oid = #{oid}")
    List<FoodOrder> getFoodOrdersByOid(String oid);
}

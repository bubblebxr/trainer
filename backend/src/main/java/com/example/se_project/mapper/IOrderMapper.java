package com.example.se_project.mapper;

import com.example.se_project.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface IOrderMapper {
    @Select("select * from orders where uid = #{uid} and orderStatus = #{status} and orderType = #{type}")
    List<Order> getOrdersByUidAndStatus(String uid, Order.OrderStatus status, Order.OrderType type);

    @Select("select * from orders where uid = #{uid} and orderType = #{type}")
    List<Order> getOrderByUid(String uid, Order.OrderType type);
    @Select("select * from orders where oid = #{oid} and uid = #{uid}")
    Order getOrderByOidAndUid(String oid, String uid);

    @Select("select * from orders where oid = #{oid}")
    Order getOrder(String oid);

    @Insert("insert into orders (oid, uid, billTime, total, orderStatus, orderType)" +
            "        values" +
            "(#{oid}, #{uid}, #{billTime}, #{total}, #{orderStatus}, #{orderType})")
    void addOrder(Order order);

    @Update("update orders set orderStatus = 'Canceled', cancelTime = NOW() where oid = #{oid}")
    void cancelOrder(Order order);

    @Delete("delete from orders where oid = #{oid}")
    void deleteOrder(Order order);

    @Update("update orders set cancelTime = #{cancelTime} where oid=#{oid}")
    void setCancelTime(String oid,String cancelTime);

    @Select("select oid,orderStatus,total,billTime from orders where uid=#{userID} and orderType='Train'")
    List<Map<String,Object>> getIdByUid(String userID);

    @Select("select oid from orders where uid=#{userID} and orderStatus=#{status} and orderType='Train'")
    List<Map<String,Object>> getIdByUidAndStatus(String userID,String status);
}

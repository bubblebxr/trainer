package com.buaa.werwertrip.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HotelControllerTest {

    @Autowired
    private HotelController hotelController;

    @Test
    void hotelQuery() {
        // 没有可获取的酒店信息
        Object hotelList1 = hotelController.hotelQuery("%E4%B8%8A%E6%B5%B7%E5%B8%82","2024-08-03","2024-08-04","lowprice").get("result");
        assertInstanceOf(ArrayList.class,hotelList1);
        ArrayList<?> arrayList1 = (ArrayList<?>) hotelList1;
        assertTrue(arrayList1.isEmpty());

        // 有可获取的信息
        Object hotelList2 = hotelController.hotelQuery("%E4%B8%8A%E6%B5%B7%E5%B8%82","2024-06-03","2024-06-04","lowprice").get("result");
        assertInstanceOf(ArrayList.class,hotelList2);
        ArrayList<?> arrayList2 = (ArrayList<?>) hotelList2;
        assertFalse(arrayList2.isEmpty());
    }

    @Test
    void getHotelDetail() {
        assertNotNull(hotelController.getHotelDetail("H00001",true,true,true,"2024-06-03","2024-06-04"));
    }

    @Test
    void getPlaces() {
    }

    @Test
    void submitHotelOrder() {
    }

    @Test
    void getHotelOrders() {
        // 无酒店订单
        Object hotelOrderMap1 = hotelController.getHotelOrders("89012319991209145X","all").get("result");
        assertInstanceOf(ArrayList.class,hotelOrderMap1);
        ArrayList<?> orderarrayList1 = (ArrayList<?>) hotelOrderMap1;
        assertTrue(orderarrayList1.isEmpty());

        // 有订单
        Object hotelOrderMap2 = hotelController.getHotelOrders("610113200403260040","all").get("result");
        assertInstanceOf(ArrayList.class,hotelOrderMap2);
        ArrayList<?> orderarrayList2 = (ArrayList<?>) hotelOrderMap2;
        assertFalse(orderarrayList2.isEmpty());
    }

    @Test
    void cancelHotelOrder() {
        // 订单不存在
        Object cancelResult1= hotelController.cancelHotelOrder("610113200403260040","2024052816414073340911").get("info");
        assertNotNull(cancelResult1);

        // 存在订单
        Object cancelResult2 = hotelController.cancelHotelOrder("610113200403260040","2024052816414073340985").get("result");
        assertTrue((Boolean)cancelResult2);
    }
}
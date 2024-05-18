package com.example.se_project.controller;

import com.example.se_project.entity.Hotel;
import com.example.se_project.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class HotelController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private Hotel current_hotel;

    // 在Controller中查询最低价
    @GetMapping("/hotel/{arrive_station}/{arrive_date}/{ldeparture_date}")
    public Map<String, Object> hotelQuery(@PathVariable String arrive_station,
                                          @PathVariable String arrive_date,
                                          @PathVariable String ldeparture_date,
                                          @RequestParam(value = "sort_type") String sort_type) {
        List<Integer> hotelList = hotelService.searchAvailableHotelId(arrive_station, arrive_date, ldeparture_date);
        List<Object> result = new ArrayList<>();
        for (Integer id : hotelList) {
            current_hotel = hotelService.searchHotel(id);
            result.add(new HashMap<>() {{
                put("name", current_hotel.getName());
                put("rank", current_hotel.getRank());
                put("stars", current_hotel.getStars());
                put("likes", current_hotel.getMessages());
                List<Object> commentInfo = new ArrayList<>();
                hotelService.searchComment(id).forEach(e -> {
                    commentInfo.add(e.getContent());
                });

                put("messages", commentInfo.size());

                List<Object> roomInfo = new ArrayList<>();
                AtomicReference<Double> miniprice = new AtomicReference<>(999999.99);
                hotelService.searchRoom(id).forEach(e -> {
                    if (e.getNum() > 0) {
                        if (miniprice.get() > Double.parseDouble(e.getPrice()))
                            miniprice.set(Double.parseDouble(e.getPrice()));
                        roomInfo.add(new HashMap<>() {{
                            put("name", e.getName());
                            put("photo", e.getPhoto());
                            put("price", e.getPrice());
                        }});
                    }
                });

                put("price", String.valueOf(miniprice.get()));
                hotelService.updateMin(id,String.valueOf(miniprice.get()));
                put("photo", current_hotel.getPhoto());
                put("description", current_hotel.getDescription());
                put("content", current_hotel.getContent());

                put("comment", commentInfo);
                put("room", roomInfo);
            }});

        }
//        if(sort_type.equals("grade"))


        return new HashMap<>() {{
            put("result", result);
        }};
    }

}

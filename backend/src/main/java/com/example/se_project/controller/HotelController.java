package com.example.se_project.controller;

import com.example.se_project.entity.Comment;
import com.example.se_project.entity.Hotel;
import com.example.se_project.entity.Room;
import com.example.se_project.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class HotelController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private Hotel current_hotel;
    @Autowired
    private Room room1, room2, room3;

    // 在Controller中查询最低价
    @GetMapping("/hotel/{arrive_station}/{arrive_date}/{ldeparture_date}")
    public Map<String, Object> hotelQuery(@PathVariable String arrive_station,
                                          @PathVariable String arrive_date,
                                          @PathVariable String ldeparture_date,
                                          @RequestParam(value = "sort_type",required = false) String sort_type) {
        arrive_station = URLDecoder.decode(arrive_station, StandardCharsets.UTF_8);
        List<Integer> hotelList = hotelService.searchAvailableHotelId(arrive_station, arrive_date, ldeparture_date);
        List<Map<String,Object>> result = new ArrayList<>();
        System.out.println(arrive_date);
        System.out.println(ldeparture_date);
        System.out.println(hotelList);
//        for (Integer id : hotelList) {
//            current_hotel = hotelService.searchHotel(id);
//            result.add(new HashMap<>() {
//                {
//                    put("name", current_hotel.getName());
//                    put("rank", current_hotel.getRank());
//                    put("stars", current_hotel.getStars());
//                    put("likes", current_hotel.getLikes());
//                    List<Object> commentInfo = new ArrayList<>();
//                    hotelService.searchComment(id).forEach(e -> {
//                        commentInfo.add(new HashMap<>() {{
//                            put("name", e.getPerson());
//                            put("comment", e.getContent());
//                            put("time", e.getTime());
//                        }});
//
//                        put("messages", commentInfo.size());
//
//                        List<Object> roomInfo = new ArrayList<>();
//                        AtomicReference<Double> miniprice = new AtomicReference<>(999999.99);
//                        Map<String, Room> map1 = new HashMap<>();
//                        Map<String, Room> map2 = new HashMap<>();
//                        Map<String, Room> map3 = new HashMap<>();
//                        hotelService.searchRoom(id).forEach(er -> {
//                            if (er.getNum() > 0) {
//                                if (miniprice.get() > Double.parseDouble(er.getPrice()))
//                                    miniprice.set(Double.parseDouble(er.getPrice()));
//
//                                if (map1.isEmpty() && er.getName().equals("标准间")) {
//                                    map1.put("标准间", er);
//                                }
//                                if (map2.isEmpty() && er.getName().equals("大床房")) {
//                                    map2.put("大床房", er);
//                                }
//                                if (map3.isEmpty() && er.getName().equals("家庭间")) {
//                                    map3.put("家庭间", er);
//                                }
//                                if (!(map1.isEmpty()) && er.getName().equals("标准间") && Double.parseDouble(er.getPrice()) < Double.parseDouble(map1.get("标准间").getPrice())) {
//                                    map1.replace("标准间", er);
//                                }
//                                if (!(map2.isEmpty()) && er.getName().equals("大床房") && Double.parseDouble(er.getPrice()) < Double.parseDouble(map2.get("大床房").getPrice())) {
//                                    map2.replace("大床房", er);
//                                }
//                                if (!(map3.isEmpty()) && er.getName().equals("家庭间") && Double.parseDouble(er.getPrice()) < Double.parseDouble(map3.get("家庭间").getPrice())) {
//                                    map3.replace("家庭间", er);
//                                }
//
//                            }
//                        });
//
//                        Room room = map1.get("标准间");
//                        Room finalRoom2 = room;
//                        roomInfo.add(new HashMap<>() {{
//                            put("name", finalRoom2.getName());
//                            put("photo", finalRoom2.getPhoto());
//                            put("price", finalRoom2.getPrice());
//                            put("num", finalRoom2.getNum());
//                        }});
//                        room = map1.get("大床房");
//
//                            Room finalRoom = room;
//                        roomInfo.add(new HashMap<>() {{
//                            put("name", finalRoom.getName());
//                            put("photo", finalRoom.getPhoto());
//                            put("price", finalRoom.getPrice());
//                            put("num", finalRoom.getNum());
//                        }});
//
//                        room = map1.get("家庭间");
//                        Room finalRoom1 = room;
//                        roomInfo.add(new HashMap<>() {{
//                            put("name", finalRoom1.getName());
//                            put("photo", finalRoom1.getPhoto());
//                            put("price", finalRoom1.getPrice());
//                            put("num", finalRoom1.getNum());
//                        }});
//
//                        put("miniprice", String.valueOf(miniprice.get()));
//                        hotelService.updateMin(id, String.valueOf(miniprice.get()));
//                        put("photo", current_hotel.getPhoto());
//                        put("position", current_hotel.getDescription());
//                        put("content", current_hotel.getContent());
//
//                        put("comments", commentInfo);
//                        put("rooms", roomInfo);
//
//
//                    });
//                }
//            });
//        }
        for (Integer id : hotelList) {
            current_hotel = hotelService.searchHotel(id);
            result.add(new HashMap<>() {{
                put("id", String.valueOf(id));
                put("name", current_hotel.getName());
                put("rank", current_hotel.getRank());
                put("stars", current_hotel.getStars());
                put("likes", current_hotel.getLikes());
                List<Comment> commentInfo = new ArrayList<>();
                // commentInfo.add(e.getContent());
                //                    commentInfo.add(new HashMap<>() {{
                //                        put("name", e.getPerson());
                //                        put("comment", e.getContent());
                //                        put("time", e.getTime());
                //                    }});
                commentInfo.addAll(hotelService.searchComment(id));

                put("messages", commentInfo.size());

                List<Object> roomInfo = new ArrayList<>();

                AtomicReference<Double> miniprice = new AtomicReference<>(999999.99);
                room1.setPrice("9999999.99");
                room2.setPrice("9999999.99");
                room3.setPrice("9999999.99");
                hotelService.searchRoom(id).forEach(e -> {
                    if (e.getNum() > 0) {
                        if (miniprice.get() > Double.parseDouble(e.getPrice())) {
                            miniprice.set(Double.parseDouble(e.getPrice()));
                            System.out.println(e.getDate());
                            System.out.println(e.getNum());
                        }
                        if (e.getName().equals("标准间") && Double.parseDouble(e.getPrice()) < Double.parseDouble(room1.getPrice())) {
                            room1.setPrice(e.getPrice());
                        } else if (e.getName().equals("大床房") && Double.parseDouble(e.getPrice()) < Double.parseDouble(room2.getPrice())) {
                            room2.setPrice(e.getPrice());
                        } else if (e.getName().equals("家庭间") && Double.parseDouble(e.getPrice()) < Double.parseDouble(room3.getPrice())) {
                            room3.setPrice(e.getPrice());
                        }
//                        roomInfo.add(new HashMap<>() {{
//                            put("name", e.getName());
//                            put("photo", e.getPhoto());
//                            put("price", e.getPrice());
//                            put("num",e.getNum());
//                        }});
                    }
                });
                System.out.println(room1.getName());
                roomInfo.add(new HashMap<>() {{
                            put("name", room1.getName());
                            put("photo", room1.getPhoto());
                            put("price", room1.getPrice());
                            put("num",room1.getNum());
                        }});
                roomInfo.add(new HashMap<>() {{
                    put("name", room2.getName());
                    put("photo", room2.getPhoto());
                    put("price", room2.getPrice());
                    put("num",room2.getNum());
                }});
                roomInfo.add(new HashMap<>() {{
                    put("name", room3.getName());
                    put("photo", room3.getPhoto());
                    put("price", room3.getPrice());
                    put("num",room3.getNum());
                }});

//                roomInfo.add(room1);
//                roomInfo.add(room2);
//                roomInfo.add(room3);
                put("miniprice", String.valueOf(miniprice.get()));
                hotelService.updateMin(id, String.valueOf(miniprice.get()));
                put("photo", current_hotel.getPhoto());
                put("position", current_hotel.getDescription());
                put("content", current_hotel.getContent());

                put("comments", commentInfo);
                put("rooms", roomInfo);
            }});

        }
        if (sort_type.equals("price")) {
//            Comparator<Object> priceComparator = (map1, map2) -> {
//                double price1 = Double.parseDouble((Map<String, Object>)map1.get("miniprice").toString());
//                double price2 = Double.parseDouble(map2.get("miniprice").toString());
//                return Double.compare(price1, price2);
//            };
//            Comparator<? super Object> priceComparatorWildcard = priceComparator;
//            result.sort(priceComparatorWildcard);
            Comparator<Map<String, Object>> priceComparator = (map1, map2) -> {
                double price1 = Double.parseDouble(map1.get("miniprice").toString());
                double price2 = Double.parseDouble(map2.get("miniprice").toString());
                return Double.compare(price1, price2);
            };

            result.sort(priceComparator);
        } else if (sort_type.equals("grade")) {
//            Comparator<Map<String, Object>> gradeComparator = (map1, map2) -> {
//                double grade1 = Double.parseDouble(map1.get("rank").toString());
//                double grade2 = Double.parseDouble(map2.get("rank").toString());
//                return Double.compare(grade1, grade2);
//            };
//            Comparator<? super Object> gradeComparatorWildcard = gradeComparator;
//            result.sort(gradeComparatorWildcard);
            Comparator<Map<String, Object>> rankComparator = (map1, map2) -> {
                double price1 = Double.parseDouble(map1.get("rank").toString());
                double price2 = Double.parseDouble(map2.get("rank").toString());
                return Double.compare(price1, price2);
            };

            result.sort(rankComparator);
        }
        else if(sort_type.equals("likes")){
            Comparator<Map<String, Object>> likesComparator = (map1, map2) -> {
                double price1 = Double.parseDouble(map1.get("likes").toString());
                double price2 = Double.parseDouble(map2.get("likes").toString());
                return Double.compare(price1, price2);
            };

            result.sort(likesComparator);
        }



        return new HashMap<>() {{
            put("result", result);
        }};
    }

    @GetMapping("getPlaces")
    public Map<String, Object> getPlaces() {
        List<Object> places = new ArrayList<>();
        hotelService.getPlaces().forEach(e->{
            places.add(new HashMap<>(){{
                put("place", e);
            }});
        });
        return new HashMap<>(){{
            put("places", places);
        }};
    }
}

package com.example.se_project.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.se_project.entity.Comment;
import com.example.se_project.entity.Hotel;
import com.example.se_project.entity.Room;
import com.example.se_project.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
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
                                          @RequestParam(value = "sort_type", required = false) String sort_type) {
        arrive_station = URLDecoder.decode(arrive_station, StandardCharsets.UTF_8);
        List<Map<String, Object>> result = new ArrayList<>();

        List<String> hotelList = hotelService.searchHotelByCity(arrive_station);
        for (String id : hotelList) {
            List<Map<String, Object>> roomList = hotelService.getAvailableRoom(id, arrive_date, ldeparture_date);
            double minPrice = 9999999.99;
            for (Map<String, Object> map : roomList) {
                if (Integer.parseInt(map.get("MIN(num)").toString()) > 0 && Double.parseDouble(map.get("MIN(price)").toString()) < minPrice) {
                    minPrice = Double.parseDouble(map.get("MIN(price)").toString());
                }
            }
            if (minPrice == 9999999.99)
                continue;
            Integer messages = hotelService.getCommentNum(id);
            current_hotel = hotelService.getHotelInfo(id);
            Double finalMinPrice = minPrice;
            double rank = hotelService.getHotelRank(id);
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            //String roundedNumber = ;
            rank = Double.parseDouble(decimalFormat.format(rank));
            //System.out.println(rank);
            Double finalRank = rank;
            result.add(new HashMap<>() {{
                put("id", id.toString());
                put("name", current_hotel.getName());
                put("rank", finalRank);
                put("stars", current_hotel.getStars());
                put("likes", current_hotel.getLikes());
                put("messages", messages);
                put("miniprice", finalMinPrice.toString());
                put("photo", current_hotel.getPhoto());
                put("position", current_hotel.getPosition());
                put("content", current_hotel.getContent());
            }});

        }
        // 排序操作
        switch (sort_type) {
            case "lowprice" -> {
                Comparator<Map<String, Object>> priceComparator = (map1, map2) -> {
                    double price1 = Double.parseDouble(map1.get("miniprice").toString());
                    double price2 = Double.parseDouble(map2.get("miniprice").toString());
                    return Double.compare(price1, price2);
                };
                result.sort(priceComparator);
            }
            case "rank" -> {
                Comparator<Map<String, Object>> rankComparator = (map1, map2) -> {
                    double rank1 = Double.parseDouble(map1.get("rank").toString());
                    double rank2 = Double.parseDouble(map2.get("rank").toString());
                    return Double.compare(rank2, rank1);
                };
                result.sort(rankComparator);
            }
            case "likes" -> {
                Comparator<Map<String, Object>> likesComparator = (map1, map2) -> {
                    double likes1 = Double.parseDouble(map1.get("likes").toString());
                    double likes2 = Double.parseDouble(map2.get("likes").toString());
                    return Double.compare(likes2, likes1);
                };
                result.sort(likesComparator);
            }
        }

        return new HashMap<>() {{
            put("result", result);
        }};
    }

    @GetMapping("/hotel_detail")
    public Map<String, Object> getHotelDetail(@RequestParam(value = "hotel_id") String id,
                                              @RequestParam(value = "double_choose", defaultValue = "true") Boolean double_choose,
                                              @RequestParam(value = "big_choose", defaultValue = "true") Boolean big_choose,
                                              @RequestParam(value = "family_choose", defaultValue = "true") Boolean family_choose,
                                              @RequestParam(value = "check_in") String check_in,
                                              @RequestParam(value = "check_out") String check_out) {
        //List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> roomList = hotelService.getRoomDetail(id, check_in, check_out);

        List<Object> roomInfo = new ArrayList<>();
        for (Map<String, Object> map : roomList) {
            System.out.println(map);
            if (map.get("name").equals("标准双人间") && double_choose) {
                roomInfo.add(new HashMap<>() {{
                    put("name", map.get("name"));
                    put("photo", map.get("photo"));
                    put("price", map.get("MIN(price)"));
                    put("num", "余" + map.get("MIN(num)") + "间");
                    put("size", map.get("size") + "平米");
                    put("others", map.get("others"));
                    put("bed-size", map.get("bedSize"));
                }});
            } else if (map.get("name").equals("大床房") && big_choose) {
                roomInfo.add(new HashMap<>() {{
                    put("name", map.get("name"));
                    put("photo", map.get("photo"));
                    put("price", map.get("MIN(price)"));
                    put("num", "余" + map.get("MIN(num)") + "间");
                    put("size", map.get("size") + "平米");
                    put("others", map.get("others"));
                    put("bed-size", map.get("bedSize"));
                }});
            } else if (map.get("name").equals("家庭房") && family_choose) {
                roomInfo.add(new HashMap<>() {{
                    put("name", map.get("name"));
                    put("photo", map.get("photo"));
                    put("price", map.get("MIN(price)"));
                    put("num", "余" + map.get("MIN(num)") + "间");
                    put("size", map.get("size") + "平米");
                    put("others", map.get("others"));
                    put("bed-size", map.get("bedSize"));
                }});
            }
        }
        Integer messages = hotelService.getCommentNum(id);
        current_hotel = hotelService.getHotelInfo(id);
        List<Object> commentInfo = new ArrayList<>();
        hotelService.searchComment(id).forEach(e -> {
            commentInfo.add(new HashMap<>() {{
                put("name", e.getName());
                put("time", e.getTime());
                put("content", e.getContent());
                put("place", e.getPlace());
                put("room", e.getRoom());
                put("rank", e.getGrade());
                put("photo", e.getPhoto());
            }});
        });
        double rank = hotelService.getHotelRank(id);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        //String roundedNumber = ;
        rank = Double.parseDouble(decimalFormat.format(rank));
        //System.out.println(rank);
        Double finalRank = rank;
        List<String> photoInfo = hotelService.getHotelPhoto(id);

        Map<String, Object> othersInfo = new HashMap<>();
        othersInfo.put("phone", current_hotel.getPhone());
        othersInfo.put("set_time", current_hotel.getSetTime());
        othersInfo.put("description", current_hotel.getDescription());
        othersInfo.put("breakfast_description", current_hotel.getBreakfastDescription());
        othersInfo.put("age_notion", current_hotel.getAgeNotion());
        othersInfo.put("checkin_time", current_hotel.getCheckinTime());
        othersInfo.put("checkout_time", current_hotel.getCheckoutTime());

        result.put("name", current_hotel.getName());
        result.put("rank", finalRank);
        result.put("stars", current_hotel.getStars());
        result.put("likes", current_hotel.getLikes());
        result.put("messages", messages);
        result.put("photos", photoInfo);
        //put("miniprice", finalMinPrice.toString());
        //put("photo", current_hotel.getPhoto());
        result.put("position", current_hotel.getPosition());
        //put("content", current_hotel.getContent());
        result.put("comments", commentInfo);
        result.put("rooms", roomInfo);
        result.put("others", othersInfo);

//        result.add(new HashMap<>() {{
//            //put("id", id.toString());
//            put("name", current_hotel.getName());
//            put("rank", finalRank);
//            put("stars", current_hotel.getStars());
//            put("likes", current_hotel.getLikes());
//            put("messages", messages);
//            put("photos",photoInfo);
//            //put("miniprice", finalMinPrice.toString());
//            //put("photo", current_hotel.getPhoto());
//            put("position", current_hotel.getPosition());
//            //put("content", current_hotel.getContent());
//            put("comments",commentInfo);
//            put("rooms",roomInfo);
//            put("others",othersInfo);
//        }});
        return result;
    }

    @GetMapping("getPlaces")
    public Map<String, Object> getPlaces() {
        List<Object> places = new ArrayList<>();
        hotelService.getPlaces().forEach(e -> {
            places.add(new HashMap<>() {{
                put("place", e);
            }});
        });
        return new HashMap<>() {{
            put("places", places);
        }};
    }
}

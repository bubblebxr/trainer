package com.example.se_project.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.se_project.entity.*;
import com.example.se_project.mapper.IHotelMapper;
import com.example.se_project.mapper.IMessageMapper;
import com.example.se_project.service.IHotelService;
import com.example.se_project.service.IMessageService;
import com.example.se_project.service.IOrderService;
import com.example.se_project.service.IUserService;
import com.example.se_project.service.Impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class HotelController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private Hotel current_hotel;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IUserService userService;
    @Autowired
    private EmailService emailService;

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
                    put("price", Double.parseDouble(map.get("MIN(price)").toString()));
                    put("num", Integer.parseInt(map.get("MIN(num)").toString()));
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

    @PostMapping("/hotel/bill")
    public Map<String, Object> submitHotelOrder(@RequestBody Map<String, Object> map) {
        List<Map<String, String>> customers = (List<Map<String, String>>) map.get("customers");
        String id = map.get("hotel_id").toString();
        String userId = (String) map.get("id");
        String checkinTime = (String) map.get("checkin_time");
        String checkoutTime = (String) map.get("checkout_time");
        Integer roomNum = (Integer) map.get("room_num");
        Integer type = (Integer) map.get("room_type");
        String roomType = "";
        if (type == 1)
            roomType = "标准双人间";
        else if (type == 2)
            roomType = "大床房";
        else if (type == 3)
            roomType = "家庭房";
        Double total = (Double) map.get("money");

        String oid = Order.generateOrderId();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String formattedDate = formatter.format(date);

        orderService.addOrder(new Order(oid, userId, formattedDate, total, Order.OrderStatus.Paid, Order.OrderType.Hotel));
        for (Map<String, String> customer : customers) {
            hotelService.addHotelorderDetail(id, oid, checkinTime, checkoutTime, roomNum, roomType, customer.get("name"), customer.get("id"));
        }

        String content = "【WerwerTrip】您已成功预订" + hotelService.getHotelName(id).get("name") + "，入住时间" + checkinTime + "--" + checkoutTime + "，祝您旅途愉快。";
        messageService.addMessage(userId, Message.generateMessageId(), oid, "酒店订单支付成功", formattedDate, content, false, 4);
        emailService.sendSimpleMail(userService.getEmail(userId), "酒店订单支付成功", content);
        return new HashMap<>() {{
            put("result", true);
        }};

    }

    @GetMapping("/hotel/orders/{userID}/{status}")
    Map<String, Object> getHotelOrders(@PathVariable String userID,
                                       @PathVariable String status) {
        List<Order> orders = switch (status) {
            case "paid" -> orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Paid, Order.OrderType.Hotel);
            case "cancel" ->
                    orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Canceled, Order.OrderType.Hotel);
            case "done" -> orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Done, Order.OrderType.Hotel);
            default -> orderService.getOrderByUid(userID, Order.OrderType.Hotel);
        };

        List<Object> result = new ArrayList<>();
        orders.forEach(order -> {
            HashMap<String, Object> map = new HashMap<>();
            String oid = order.getOid();
            List<Map<String, Object>> details = hotelService.getHotelOrderDetail(oid);

            String hotelId = details.get(0).get("id").toString();
            Map<String, String> hotel = hotelService.getHotelName(hotelId);
            String checkinTime = details.get(0).get("checkinTime").toString();
            String checkoutTime = details.get(0).get("checkoutTime").toString();
            map.put("oid", oid);
            map.put("time", order.getBillTime());
            map.put("name", hotel.get("name"));
            map.put("position", map.get("position"));
            map.put("check_in", checkinTime);
            map.put("check_out", checkoutTime);
            map.put("money", order.getTotal());
            map.put("roomtype", details.get(0).get("roomType"));
            List<Object> customers = new ArrayList<>();

            details.forEach(detail -> {
                customers.add(detail.get("name"));
            });
            map.put("customers", customers);

            map.put("num", details.get(0).get("roomNum"));
            map.put("status", switch (order.getOrderStatus()) {
                case Paid -> "已支付";
                case Done -> "已完成";
                case Canceled -> "已取消";
            });
            result.add(map);
        });

        return new HashMap<>() {{
            put("result", result);
        }};
    }

    @PostMapping("/hotel/cancel/{userID}/{oid}")
    public Map<String, String> cancelHotelOrder(@PathVariable String userID,
                                                @PathVariable String oid) {

        Order order = orderService.getOrderByOidAndUid(oid, userID);
        if (order == null) {
            return new HashMap<>() {{
                put("info", "订单不存在");
            }};
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String formattedDate = formatter.format(date);
            orderService.cancelOrder(order);
            orderService.setCancelTime(oid, formattedDate);
            // 发送(酒店)订单取消成功通知
            Map<String, Object> hotelMap = hotelService.getHotelOrderDetail(oid).get(0);
            String hotelId = hotelMap.get("id").toString();
            String checkinTime = hotelMap.get("checkinTime").toString();
            String checkoutTime = hotelMap.get("checkoutTime").toString();

            String content = "【WerwerTrip】您已成功取消" + hotelService.getHotelName(hotelId).get("name") + "" + checkinTime + "--" + checkoutTime + "的订单";
            messageService.addMessage(userID, Message.generateMessageId(), oid, "酒店订单取消成功", formattedDate, content, false, 4);
            emailService.sendSimpleMail(userService.getEmail(userID), "酒店订单取消成功", content);
            return new HashMap<>() {{
                put("cancel_time", formattedDate);
            }};
        }

    }
}

package com.example.se_project.controller;

import com.example.se_project.entity.Order;
import com.example.se_project.entity.Stopover;
import com.example.se_project.entity.Train;
import com.example.se_project.mapper.IOrderMapper;
import com.example.se_project.service.IOrderService;
import com.example.se_project.service.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TrainController {
    @Autowired
    private ITrainService trainService;
    @Autowired
    private IOrderService orderService;

    // isGD:0高铁 1火车 2全选
    // sort_type:1start_time升序,2start_tiem降序,3duration升序
    // seat_type:
    // is_Hide: true隐藏冲突列车
    @PostMapping("/trains/{start_city}/{arrive_city}/{date}")
    public Map<String, Object> trainQuery(
            @PathVariable String start_city,
            @PathVariable String arrive_city,
            @PathVariable String date,
            @RequestParam(value = "is_GD", defaultValue = "2") Integer is_GD,
            @RequestParam(value = "sort_type", defaultValue = "1") Integer sort_type,
            @RequestParam(value = "seat_type", defaultValue = "true,true,true,true,true,true") List<Boolean> seat_type,
            @RequestParam(value = "isHide", defaultValue = "true") Boolean isHide) {
        // 解码路径变量
        start_city = URLDecoder.decode(start_city, StandardCharsets.UTF_8);
        arrive_city = URLDecoder.decode(arrive_city, StandardCharsets.UTF_8);

        List<Train> trains = trainService.searchTrain(start_city, arrive_city, date,
                is_GD, sort_type, seat_type, isHide);
//        return new HashMap<>(){{
//            put("start", start_city);
//            put("arrive", arrive_city);
//            put("date", date);
//            put("is_GD",is_GD);
//            put("sort", sort_type);
//            put("seat", seat_type);
//            put("isHide", isHide);
//            put("trains", trains);
//        }};
        List<Object> result = new ArrayList<>();
        trains.forEach(e -> {
            result.add(new HashMap<>() {{
                String trainId = e.getTrainId();
                put("tid", trainId);
                put("start_time", e.getStartTime());
                put("arrive_time", e.getArrivalTime());
                put("time", e.getDuration());
                put("start_station", e.getStartStation());
                put("arrive_station", e.getArrivalStation());

                if (seat_type.get(0)) {
                    put("business", new HashMap<>() {{
                        put("price", e.getBusinessSeatPrice());
                        put("remain", e.getBusinessSeatSurplus());
                    }});
                }
                if (seat_type.get(1)) {
                    put("one", new HashMap<>() {{
                        put("price", e.getFirstClassSeatPrice());
                        put("remain", e.getFirstClassSeatSurplus());
                    }});
                }
                if (seat_type.get(2)) {
                    put("two", new HashMap<>() {{
                        put("price", e.getSecondClassSeatPrice());
                        put("remain", e.getSecondClassSeatSurplus());
                    }});
                }
                if (seat_type.get(3)) {
                    put("soft_sleeper", new HashMap<>() {{
                        put("price", e.getSoftSleeperPrice());
                        put("remain", e.getSoftSleeperSurplus());
                    }});
                }
                if (seat_type.get(4)) {
                    put("hard_sleeper", new HashMap<>() {{
                        put("price", e.getHardSleeperPrice());
                        put("remain", e.getHardSleeperSurplus());
                    }});
                }
                if (seat_type.get(5)) {
                    put("hard_seat", new HashMap<>() {{
                        put("price", e.getHardSeatPrice());
                        put("remain", e.getHardSeatSurplus());
                    }});
                }

                List<Object> stationInfo = new ArrayList<>();
                trainService.searchStopover(trainId).forEach(e -> {
                    stationInfo.add(new HashMap<>() {{
                        put("id", e.getStationId());
                        put("name", e.getStationName());
                        put("arrive", e.getArriveTime());
                        put("departure", e.getLeaveTime());
                        put("stop", e.getDuration());
                    }});
                });
                put("station_info", stationInfo);
            }});
        });
        return new HashMap<>() {{
            put("result", result);
        }};
    }

    @PostMapping("/ticket/bill")
    public Map<String, Object> submitTrainOrder(@RequestBody Map<String, Object> map) {
        List<Map<String, String>> persons = (List<Map<String, String>>) map.get("person");
        String userId = (String) map.get("userID");
        String trainId = (String) map.get("tid");
        String trainDate = (String) map.get("date");
        String billTime = (String) map.get("bill_time");
        Double total = Double.parseDouble((String) map.get("sum_price"));

        String oid = Order.generateOrderId();

        orderService.addOrder(new Order(oid, userId, billTime, total, Order.OrderStatus.Paid, Order.OrderType.Train));

        for (Map<String, String> person : persons) {
            trainService.addTrainOrderDetail(oid, trainId, trainDate, person.get("name"), person.get("identification"), person.get("seatType"));
        }
        return new HashMap<>() {{
            put("info", "下单成功！");
        }};
    }

    @PostMapping("/ticket/cancel/{userID}/{oid}")
    public Map<String, Object> cancelTrainOrder(@PathVariable String userID,
                                                @PathVariable String oid,
                                                @RequestBody String cancelTime) {
        Order order = orderService.getOrderByOidAndUid(oid, userID);
        if (order == null) {
            return new HashMap<>() {{
                put("info", "订单不存在");
                put("result", false);
            }};
        } else {
            orderService.cancelOrder(order);
            orderService.setCancelTime(oid,cancelTime);
            return new HashMap<>() {{
                put("info", "取消成功");
                put("result", true);
            }};
        }
    }

}

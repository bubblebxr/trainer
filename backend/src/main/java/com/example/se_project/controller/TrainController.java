package com.example.se_project.controller;

import com.example.se_project.entity.Order;
import com.example.se_project.entity.Stopover;
import com.example.se_project.entity.Train;
import com.example.se_project.entity.TrainOrder;
import com.example.se_project.mapper.IOrderMapper;
import com.example.se_project.service.IOrderService;
import com.example.se_project.service.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
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
            trainService.addTrainOrderDetail(oid, trainId, trainDate, person.get("name"), person.get("identification"), person.get("seat_type"));
        }
        return new HashMap<>() {{
            put("info", "下单成功！");
        }};
    }

    @PostMapping("/ticket/cancel/{userID}/{oid}")
    public Map<String, Object> cancelTrainOrder(@PathVariable String userID,
                                                @PathVariable String oid,
                                                @RequestBody Map<String, Object> map) {
        Order order = orderService.getOrderByOidAndUid(oid, userID);
        if (order == null) {
            return new HashMap<>() {{
                put("info", "订单不存在");
                put("result", false);
            }};
        } else {
            orderService.cancelOrder(order);
            String cancelTime = map.get("cancel_time").toString();
            orderService.setCancelTime(oid, cancelTime);
            return new HashMap<>() {{
                put("info", "取消成功");
                put("result", true);
            }};
        }
    }

    @GetMapping("/ticket/orders/{userID}/{status}")
    Map<String, Object> getOrders(@PathVariable String userID,
                                  @PathVariable String status) {
        List<Order> orders = switch (status) {
            case "paid" -> orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Paid, Order.OrderType.Train);
            case "cancel" ->
                    orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Canceled, Order.OrderType.Train);
            case "done" -> orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Done, Order.OrderType.Train);
            default -> orderService.getOrderByUid(userID, Order.OrderType.Train);
        };

        List<Object> result = new ArrayList<>();
        orders.forEach(order -> {
            HashMap<String, Object> map = new HashMap<>();
            String oid = order.getOid();
            List<TrainOrder> trainOrders = trainService.getTrainOrdersByOid(oid);

            String tid = trainOrders.get(0).getTrainId();
            String date = trainOrders.get(0).getTrainDate();
            Train train = trainService.getTrainByTidAndDate(tid, date);

            map.put("tid", tid);
            map.put("cancel_time", order.getCancelTime());
            map.put("oid", order.getOid());
            map.put("start_time", train.getStartTime());
            map.put("start_station", train.getStartStation());
            map.put("arrive_station", train.getArrivalStation());
            map.put("time", train.getDuration());
            map.put("arrive_time", train.getArrivalTime());
            map.put("date", date);
            map.put("order_time", order.getBillTime());
            map.put("status", switch (order.getOrderStatus()) {
                case Paid -> "已支付";
                case Done -> "已完成";
                case Canceled -> "已取消";
            });
            map.put("sum_price", order.getTotal());

            List<Object> person = new ArrayList<>();

            trainOrders.forEach(trainOrder -> {
                person.add(new HashMap<>() {{
                    put("name", trainOrder.getName());
                    put("identification", trainOrder.getIdentification());
                    put("seat_type", trainOrder.getSeatType());
                }});
            });

            map.put("person", person);
            result.add(map);
        });

        return new HashMap<>() {{
            put("result", result);
        }};
    }

    @GetMapping("/getTid/{userID}")
    public Map<String, Object> getSelfOrder(@PathVariable String userID,
                                                  @RequestParam(value = "status", defaultValue = "all") String status) {
        List<Map<String, String>> orders = new ArrayList<>();
        //List<String>
        if (status.equals("paid"))
            status = "Paid";
        else if (status.equals("cancel"))
            status = "Canceled";
        else if (status.equals("done"))
            status = "Done";
        if (status.equals("all"))
            orders = orderService.getIdByUid(userID);
        else
            orders = orderService.getIdByUidAndStatus(userID, status);
        List<Object> result = new ArrayList<>();
        for (Map<String, String> order : orders) {
            Map<String, Object> orderMap = trainService.getSelfOrderDetail(order.get("oid"), userID);
            if (orderMap != null) {
                result.add(new HashMap<>() {{
                    put("tid", orderMap.get("trainId"));
                    put("oid", order);
                    put("status", order.get("orderStatus"));
                    Map<String, Object> trainMap = trainService.getTrainByIdAndDate(orderMap.get("trainId").toString(), orderMap.get("trainDate").toString());
                    put("start_station", trainMap.get("startStation"));
                    put("start_time", trainMap.get("startTime"));
                    put("arrive_time", trainMap.get("arrivalTime"));
                    put("order_time", order.get("billTime"));
                    put("time", trainMap.get("duration"));
                    put("arrive_station", trainMap.get("arriveStation"));
                    put("date", orderMap.get("trainDate"));
                    put("seat_type", orderMap.get("seatType"));
                    put("price", order.get("total"));
                }});
            }

        }
        return new HashMap<>(){{
            put("result",result);
        }};

    }

}

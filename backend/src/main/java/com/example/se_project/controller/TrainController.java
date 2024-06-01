package com.example.se_project.controller;

import com.example.se_project.entity.*;
import com.example.se_project.service.*;
import com.example.se_project.service.Impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class TrainController {
    @Autowired
    private ITrainService trainService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFoodService foodService;

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
//        System.out.println("input"+start_city+arrive_city);
//        System.out.println("start end: " + URLEncoder.encode("上海", StandardCharsets.UTF_8) +"/"+ URLEncoder.encode("北京",StandardCharsets.UTF_8));

        List<Train> trains = trainService.searchTrain(start_city, arrive_city, date,
                is_GD, sort_type, seat_type, isHide);
        List<Object> result = new ArrayList<>();
        for (Train e : trains) {
            boolean[] haveTicketsToShow = {false};
            if (seat_type.get(0) && e.getBusinessSeatSurplus() > 0) {
                haveTicketsToShow[0] = true;
            } else if (seat_type.get(1) && e.getFirstClassSeatSurplus() > 0) {
                haveTicketsToShow[0] = true;
            } else if (seat_type.get(2) && e.getSecondClassSeatSurplus() > 0) {
                haveTicketsToShow[0] = true;
            } else if (seat_type.get(3) && e.getSoftSleeperSurplus() > 0) {
                haveTicketsToShow[0] = true;
            } else if (seat_type.get(4) && e.getHardSleeperSurplus() > 0) {
                haveTicketsToShow[0] = true;
            } else if (seat_type.get(5) && e.getHardSeatSurplus() > 0) {
                haveTicketsToShow[0] = true;
            }
            if (!haveTicketsToShow[0]) {
                continue;
            }

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
        }
        return new HashMap<>() {{
            put("result", result);
        }};
    }

    // 火车结算
    @PostMapping("/ticket/bill")
    public Map<String, Object> submitTrainOrder(@RequestBody Map<String, Object> map) {
        List<Map<String, String>> persons = (List<Map<String, String>>) map.get("person");
        String userId = (String) map.get("userID");
        String trainId = (String) map.get("tid");
        String trainDate = (String) map.get("date");
        //String billTime = (String) map.get("bill_time");
        Double total = Double.parseDouble((String) map.get("sum_price"));

        String oid = Order.generateOrderId();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String formattedDate = formatter.format(date);


        int num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0;
        for (Map<String, String> person : persons) {
            for(TrainOrder trainOrder: trainService.getTrainOrderByTrainAndIdentification(trainId, trainDate, person.get("identification"))) {
                if (orderService.getOrder(trainOrder.getOid()).getOrderStatus() == Order.OrderStatus.Paid) {
                    return new HashMap<>() {{
                        put("info", "下单失败");
                    }};
                }
            }
        }
        orderService.addOrder(new Order(oid, userId, formattedDate, total, Order.OrderStatus.Paid, Order.OrderType.Train));

        for (Map<String, String> person : persons) {
            String type = person.get("seat_type");
            trainService.addTrainOrderDetail(oid, trainId, trainDate, person.get("name"), person.get("identification"), type);
            switch (type) {
                case "商务座":
                    num1 += 1;
                    break;
                case "一等座":
                    num2 += 1;
                    break;
                case "二等座":
                    num3 += 1;
                    break;
                case "软卧":
                    num4 += 1;
                    break;
                case "硬卧":
                    num5 += 1;
                    break;
                case "硬座":
                    num6 += 1;
                    break;
            }
        }
        //火车数量--
        trainService.updateTrainSeat(trainId, trainDate, num1, num2, num3, num4, num5, num6);


//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        String formattedDate = formatter.format(date);

        Map<String, Object> trainMap = trainService.getTrainByIdAndDate(trainId, trainDate);
        LocalDateTime startTime = (LocalDateTime) trainMap.get("startTime");
        String formattedStartTime = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String content = "【WerwerTrip】您已成功购买" + trainDate + "由" + trainMap.get("startStation") + "站发往" + trainMap.get("arrivalStation") + "站的" + trainId + "次列车车票，发车时间" + formattedStartTime + "。请合理安排出行时间。";
        String Mcontent = "您已成功购买" + trainDate + "由" + trainMap.get("startStation") + "站发往" + trainMap.get("arrivalStation") + "站的" + trainId + "次列车车票，发车时间" + formattedStartTime + "。请合理安排出行时间。";

        messageService.addMessage(userId, Message.generateMessageId(), oid, "车票订单支付成功", formattedDate, Mcontent, false, "3");

        emailService.sendSimpleMail(userService.getEmail(userId), "火车订单支付成功", content);
        return new HashMap<>() {{
            put("info", "下单成功！");
        }};
    }

    @PostMapping("/ticket/cancel/{userID}/{oid}")
    public Map<String, Object> cancelTrainOrder(@PathVariable String userID,
                                                @PathVariable String oid) {
        Order order = orderService.getOrderByOidAndUid(oid, userID);
        if (order == null) {
            return new HashMap<>() {{
                put("info", "订单不存在");
                put("result", false);
            }};
        } else {
            orderService.cancelOrder(order);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String formattedDate = formatter.format(date);

            orderService.setCancelTime(oid, formattedDate);

            List<TrainOrder> trainMap = trainService.getTrainOrdersByOid(oid);

            int num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0;
            for (TrainOrder orderDetail : trainMap) {
                String type = orderDetail.getSeatType();
                //trainService.addTrainOrderDetail(oid, trainId, trainDate, person.get("name"), person.get("identification"), type);
                switch (type) {
                    case "商务座":
                        num1 -= 1;
                        break;
                    case "一等座":
                        num2 -= 1;
                        break;
                    case "二等座":
                        num3 -= 1;
                        break;
                    case "软卧":
                        num4 -= 1;
                        break;
                    case "硬卧":
                        num5 -= 1;
                        break;
                    case "硬座":
                        num6 -= 1;
                        break;
                }
            }

            String trainId = trainMap.get(0).getTrainId();
            String trainDate = trainMap.get(0).getTrainDate();
            Map<String, Object> train = trainService.getTrainByIdAndDate(trainId, trainDate);

            // 恢复座位数
            trainService.updateTrainSeat(trainId, trainDate, num1, num2, num3, num4, num5, num6);


            // 取消该trainOrder对应的foodOrder
            foodService.getFoodOrdersByTrain(trainId, trainDate).forEach(foodOrder -> {
                orderService.cancelOrder(orderService.getOrder(foodOrder.getOid()));
            });


            //String content = "您已成功取消" +trainMap.getTrainDate() + " " + trainMap.getTrainId()+ "车次的列车" + food.getMealTime();
            String content = "【WerwerTrip】您已成功取消" + trainDate + "由" + train.get("startStation") + "站发往" + train.get("arrivalStation") + "站的" + trainId + "次列车车票";
            String Mcontent = "您已成功取消" + trainDate + "由" + train.get("startStation") + "站发往" + train.get("arrivalStation") + "站的" + trainId + "次列车车票";
            messageService.addMessage(userID, Message.generateMessageId(), oid, "火车订单取消成功", formattedDate, Mcontent, false, "3");

            emailService.sendSimpleMail(userService.getEmail(userID), "火车订单取消成功", content);

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

        // System.out.println("参数"+status);
        List<Order> orders = switch (status) {
            case "paid" -> orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Paid, Order.OrderType.Train);
            case "cancel" ->
                    orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Canceled, Order.OrderType.Train);
            case "done" -> orderService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Done, Order.OrderType.Train);
            default -> orderService.getOrderByUid(userID, Order.OrderType.Train);
        };


        List<Object> result = new ArrayList<>();
        for (Order order : orders) {
            Map<String, Object> orderMap = trainService.getSelfOrderDetail(order.getOid(), userID);
            if (orderMap != null) {
                System.out.println(order.getOrderStatus());
                result.add(new HashMap<>() {{
                    put("tid", orderMap.get("trainId"));
                    put("oid", order.getOid());
                    put("status", switch (order.getOrderStatus()) {
                        case Paid -> "已支付";
                        case Done -> "已完成";
                        case Canceled -> "已取消";
                    });
                    //put("status", finalOrderStatus);

                    Map<String, Object> trainMap = trainService.getTrainByIdAndDate(orderMap.get("trainId").toString(), orderMap.get("trainDate").toString());
                    LocalDateTime startTime = (LocalDateTime) trainMap.get("startTime");
                    String formattedStartTime = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime arrivalTime = (LocalDateTime) trainMap.get("arrivalTime");
                    String formattedArrivalTime = arrivalTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    LocalDateTime billTime = LocalDateTime.parse(order.getBillTime(), formatter);
                    String formattedBillTime = billTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    put("start_station", trainMap.get("startStation"));
                    put("start_time", formattedStartTime);
                    put("arrive_time", formattedArrivalTime);
                    put("order_time", formattedBillTime);
                    put("time", trainMap.get("duration"));
                    put("arrive_station", trainMap.get("arrivalStation"));
                    put("date", orderMap.get("trainDate"));
                    put("seat_type", orderMap.get("seatType"));
                    put("price", order.getTotal());
                }});
            }

        }
        return new HashMap<>() {{
            put("result", result);
        }};

    }

}
package com.example.se_project.controller;

import com.example.se_project.entity.Food;
import com.example.se_project.entity.FoodOrder;
import com.example.se_project.entity.Order;
import com.example.se_project.service.IFoodService;
import com.example.se_project.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class FoodController {
    @Autowired
    private IFoodService foodService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private Food food;


    @GetMapping("/food/{tid}/{date}/{time}")
    Map<String, List<Food>> getAllFood(@PathVariable String tid, @PathVariable String date, @PathVariable String time) {

        List<Food> foodList = foodService.getAllFood(tid, date, time);
        Map<String, List<Food>> resMap = new HashMap<>();
        resMap.put("result", foodList);
        return resMap;
    }

    @PostMapping("/food/bill")
    Map<String, Object> submitFoodOrder(@RequestBody Map<String, Object> map) {
        String userId = (String)map.get("userID");
        String trainId = (String)map.get("tid");
        String mealDate = (String)map.get("date");
        String mealTime = (String)map.get("time");
        String billTime = (String)map.get("bill_time");
        Double  total = Double.parseDouble((String)map.get("sum_price"));

        String oid = Order.generateOrderId();

        orderService.addOrder(new Order(oid, userId, billTime, total, Order.OrderStatus.Paid, Order.OrderType.Food));

        try {
            ((List<HashMap<String, String>>)(map.get("foods"))).forEach(e->{
                Food f = foodService.findFoodByAllKeys(trainId, mealDate, mealTime, e.get("food_name"));
                foodService.addFoodOrder(new FoodOrder(oid, e.get("food_name"),
                        Integer.parseInt(e.get("count")), trainId,  mealTime, mealDate, f.getPhoto()));
            });
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return new HashMap<>(){{
            put("info", "下单成功！");
        }};
    }

    @GetMapping("/food/orders/{userID}/{status}")
    Map<String, Object> getOrders(@PathVariable String userID,
                                  @PathVariable String status) {
        List<Order> orders;
        if (status.equals("paid")) {
            orders = foodService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Paid);
        }
        else if (status.equals("cancel")) {
            orders = foodService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Canceled);
        }
        else if (status.equals("done")) {
            orders = foodService.getOrdersByUidAndStatus(userID, Order.OrderStatus.Done);
        }
        else {
            orders = foodService.getOrderByUid(userID);
        }

        List<Object> result = new ArrayList<>();
        orders.forEach(order -> {
            HashMap<String, Object> map = new HashMap<>();

            List<FoodOrder> foodOrders = foodService.getFoodOrdersByOid(order.getOid());
            map.put("oid", order.getOid());
            map.put("order_time", order.getBillTime());

            if(order.getOrderStatus() == Order.OrderStatus.Done) {
                map.put("status", "已完成");
            }
            else if (order.getOrderStatus() == Order.OrderStatus.Paid) {
                map.put("status", "已支付");
            }
            else {
                map.put("status", "已取消");
            }
            map.put("sum_price", order.getTotal());

            List<Object> foods = new ArrayList<>();
            foodOrders.forEach(foodOrder -> {
                if(!map.containsKey("date")) {
                    map.put("date", foodOrder.getMealDate());
                }
                if(!map.containsKey("time")) {
                    map.put("time", foodOrder.getMealTime());
                }

                foods.add(new HashMap<>(){{
                    put("food_name", foodOrder.getFoodName());
                    put("count", foodOrder.getCount());
                    put("photo", foodOrder.getPhoto());
                }});
            });
            map.put("foods", foods);
            result.add(map);
        });

        return new HashMap<>() {{
            put("result", result);
        }};
    }

    @PostMapping("/food/cancel/{userID}/{oid}")
    Map<String, Object> cancelOrder(@PathVariable String userID,
                                    @PathVariable String oid) {
        Order order = orderService.getOrderByOidAndUid(oid, userID);
        if(order == null) {
            return new HashMap<>(){{
                put("info", "取消订单失败");
                put("result", false);
            }};
        } else {
            orderService.cancelOrder(order);
            return new HashMap<>(){{
                put("info", "取消订单成功");
                put("result", true);
            }};
        }
    }

    @PostMapping("/food/delete/{userID}/{oid}")
    Map<String, Object> deleteOrder(@PathVariable String userID,
                                    @PathVariable String oid) {
        Order order = orderService.getOrderByOidAndUid(oid, userID);
        if(order == null) {
            return new HashMap<>(){{
                put("info", "删除订单失败");
                put("result", false);
            }};
        } else {
            orderService.deleteOrder(order);
            return new HashMap<>(){{
                put("info", "删除订单成功");
                put("result", true);
            }};
        }
    }
}

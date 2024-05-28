package com.example.se_project.controller;

import com.example.se_project.entity.Message;
import com.example.se_project.entity.Order;
import com.example.se_project.service.IMessageService;
import com.example.se_project.service.IOrderService;
import com.example.se_project.service.ITrainService;
import com.example.se_project.service.IUserService;
import com.example.se_project.service.Impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class Reminder {
    @Autowired
    private ITrainService trainService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IMessageService messageService;

    @Scheduled(fixedRate = 60000) // 每隔1分钟执行一次
    public void remindUsers() {
        LocalDateTime now = LocalDateTime.now();
        //LocalDateTime threeHoursLater = now.plusHours(3);
        List<Map<String, Object>> orderMap = orderService.getAllTrain();
        for (Map<String, Object> order : orderMap) {
            String userId = order.get("uid").toString();
            String orderId = order.get("oid").toString();
            String email = userService.getEmail(userId);
            Map<String, Object> trainMap = trainService.getTrainIdAndDate(orderId).get(0);
            System.out.println(trainMap);

            String trainId = trainMap.get("trainId").toString();
            String trainDate = trainMap.get("trainDate").toString();
            System.out.println(trainId);
            System.out.println(trainDate);
            String startTime = trainService.getStartTime(trainId, trainDate);
            if (startTime != null) // 车次存在
            {
                System.out.println(startTime);
                LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.println(now);
                Duration duration = Duration.between(now, start);

                if (duration.isPositive() && duration.toHours() < 3 && !orderService.getMessageSend(orderId)) {
                    // 距发车时间小于3h且未发送过消息
                    String content = "【WerwerTrip】距离您预定的" + trainDate + " " + trainId + "车次的" + "列车发车时间已不足3小时，请您合理安排出行时间，以免错过列车。";
                    //System.out.println(content);
                    messageService.addMessage(userId, Message.generateMessageId(), orderId, "行程提醒", now.toString(), content, false, "2");
                    emailService.sendSimpleMail(email, "行程提醒", content);
                    orderService.setMessageHaveSend(orderId);
                }
            }
            // 车次取消
            else {
                System.out.println("车次不存在");
            }


//                // 取消火车订单
//orderService.cancelOrder((Order) order);
//// 取消餐饮订单
//                // 取消该trainOrder对应的foodOrder
//                foodService.getFoodOrdersByTrain(trainId, trainDate).forEach(foodOrder -> {
//                    orderService.cancelOrder(orderService.getOrder(foodOrder.getOid()));
//                });

        }

    }

}




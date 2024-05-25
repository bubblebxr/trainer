package com.example.se_project.controller;

import com.example.se_project.entity.Message;
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
            String trainId = trainMap.get("trainId").toString();
            String trainDate = trainMap.get("trainDate").toString();
            String startTime = trainService.getStartTime(trainId, trainDate);
            LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Duration duration = Duration.between(now, start);

            if (duration.toHours() == 2 && duration.toMinutesPart() >= 59) {
                // 在2小时59分钟到3小时之间
                String content = "距离您预定的" + trainDate + " " + trainId + "车次的" + "列车发车时间已不足3小时，请您合理安排出行时间，以免错过列车。";

                messageService.addMessage(userId, Message.generateMessageId(), orderId, "行程提醒", now.toString(), content, false, 2);
                emailService.sendSimpleMail(email, "行程提醒", content);
            }

        }

    }
}



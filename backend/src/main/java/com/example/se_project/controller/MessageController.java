package com.example.se_project.controller;

import com.example.se_project.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private IMessageService messageService;

    @GetMapping("/message/getAll/{userID}")
    public Map<String, Object> getMessage(@PathVariable String userID) {
        List<Map<String, Object>> messageMap = messageService.getMessage(userID);
        List<Object> result = new ArrayList<>();
        for(Map<String, Object> message:messageMap){
            result.add(new HashMap<>(){{
                put("orderType",message.get("orderType"));
                put("orderId",message.get("orderId"));
                put("haveRead",message.get("haveRead"));
                put("title",message.get("title"));
                LocalDateTime messageTime = (LocalDateTime) message.get("messageTime");
                String formattedMessageTime = messageTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                put("messageTime",formattedMessageTime);
                put("content",message.get("content"));
                put("mid",message.get("mid"));
            }});
        }

        return new HashMap<>() {{
            put("result",result);
        }};
    }

    @PostMapping("/message/setRead/{mid}")
    public void setHaveread(@PathVariable String mid) {

        // 标记已读
        messageService.setHaveread(mid);
    }
}

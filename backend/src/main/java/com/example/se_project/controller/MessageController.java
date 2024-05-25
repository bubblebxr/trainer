package com.example.se_project.controller;

import com.example.se_project.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private IMessageService messageService;

    @GetMapping("/message/getAll/{userID}")
    public Map<String,Object> getMessage(@PathVariable String userID){
        return new HashMap<>(){{
            put("result",messageService.getMessage(userID));
        }};
    }

    @PostMapping("/message/setRead/{mid}")
    public void setHaveread(@PathVariable String mid){

        // 标记已读
        messageService.setHaveread(mid);
    }
}

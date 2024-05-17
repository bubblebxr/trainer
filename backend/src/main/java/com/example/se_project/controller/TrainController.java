package com.example.se_project.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrainController {
    // isGD:0高铁 1火车 2全选
    // sort_type:1start_time升序,2start_tiem降序,3duration升序
    // seat_type:
    // is_Hide: true隐藏冲突列车
    @PostMapping("/trains/{start_city}/{arrive_city}/{date}")
    public void trainQuery(
            @PathVariable String start_city,
            @PathVariable String arrive_city,
            @PathVariable String date,
            @RequestParam(value = "isGD", defaultValue = "2") Integer isGD,
            @RequestParam(value = "sort_type",defaultValue = "1") Integer sort_type,
            @RequestParam(value = "seat_type",defaultValue = "true,true,true,true,true,true") List<Boolean> seat_type,
            @RequestParam(value = "isHide",defaultValue = "true") Boolean isHide){


    }

}

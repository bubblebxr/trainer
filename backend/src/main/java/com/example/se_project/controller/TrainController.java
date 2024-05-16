package com.example.se_project.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainController {
    // isGD:0高铁 1火车 2全选
    // sort_type:
    // seat_type:
    // is_Hide: true隐藏冲突列车
    @PostMapping("/trains/{start_city}/{arrive_city}/{date}")
    public void trainQuery(
            @PathVariable String start_city,
            @PathVariable String arrive_city,
            @PathVariable String date,
            @RequestParam(value = "isGD", defaultValue = "2") Integer isGD,
            @RequestParam(value = "sort_type",defaultValue = "0") Integer sort_type,

            @RequestParam(value = "isHide",defaultValue = "true") Boolean isHide){

    }

}

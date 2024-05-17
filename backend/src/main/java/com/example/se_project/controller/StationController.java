package com.example.se_project.controller;

import com.example.se_project.entity.Station;
import com.example.se_project.service.IStationService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StationController {

    @Autowired
    private IStationService stationService;

    @GetMapping("/station")
    public Map<String, Object> inquireAllStations() {
        List<Object> list = new ArrayList<>();
        stationService.inquireAllStations().forEach(e->{
            list.add(new HashMap<>(){{
                put("value", e);
            }});
        });
        return new HashMap<>(){{
            put("station", list);
        }};
    }
}

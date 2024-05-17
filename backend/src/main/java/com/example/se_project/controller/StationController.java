package com.example.se_project.controller;

import com.example.se_project.entity.Station;
import com.example.se_project.service.IStationService;
import org.json.JSONArray;
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
        JSONObject jsonObject = new JSONObject();
        JSONArray stations = new JSONArray();
        stationService.inquireAllStations().forEach(e->{
            JSONObject j = new JSONObject();
            j.put("value", e.getStationName());
            stations.put(j);
        });
        jsonObject.put("station", stations);
        return jsonObject.toMap();
    }
}

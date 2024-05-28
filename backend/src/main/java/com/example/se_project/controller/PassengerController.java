package com.example.se_project.controller;

import com.example.se_project.service.IPassengerService;
import com.example.se_project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class PassengerController {
    @Autowired
    private IPassengerService passengerService;
    @Autowired
    private IUserService userService;

    @PostMapping("/insertPassengers/{id}")
    public Map<String, Boolean> addPassenger(@PathVariable String id,
                                             @RequestParam(value = "name") String name,
                                             @RequestParam(value = "identification") String identification,
                                             @RequestParam(value = "phone") String phone) {
        Integer res = passengerService.addPassenger(name, identification, phone, id);
        boolean result;
        result = res != 0;
        return new HashMap<>() {{
            put("info", result);
        }};


    }

    @PostMapping("/updatePassengers/{id}")
    public Map<String, Boolean> updatePassenger(@PathVariable String id,
                                                @RequestParam(value = "oldidentification") String oldidentification,
                                                @RequestParam(value = "newname", required = false, defaultValue = "") String newname,
                                                @RequestParam(value = "newidentification", required = false, defaultValue = "") String newidentification,
                                                @RequestParam(value = "newphone", required = false, defaultValue = "") String newphone) {

        if (newname.isEmpty()) newname = null;
        if (newidentification.isEmpty()) newidentification = null;
        if (newphone.isEmpty()) newphone = null;
        String finalNewname = newname;
        String finalNewidentification = newidentification;
        String finalNewphone = newphone;

        Integer res = passengerService.updatePassenger(id, oldidentification, finalNewname, finalNewidentification, finalNewphone);
        boolean result;
        result = res != 0;
        return new HashMap<>() {{
            put("info", result);
        }};
    }

    @PostMapping("/passengersInfo/{id}")
    public Map<String, Object> showPassengerInfo(@PathVariable String id) {
        return new HashMap<>() {{
            put("passenger", passengerService.showPassengerInfo(id));
        }};
    }

    @PostMapping("/deletePassengers/{id}")
    public Map<String, Boolean> deletePassenger(@PathVariable String id,
                                                @RequestParam String name,
                                                @RequestParam String identification) {
        String passengerName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        Integer res = passengerService.deletePassenger(id, passengerName, identification);
        boolean result;
        result = res != 0;
        return new HashMap<>() {{
            put("info", result);
        }};
    }
}

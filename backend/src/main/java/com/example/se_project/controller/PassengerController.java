package com.example.se_project.controller;

import com.example.se_project.service.IPassengerService;
import com.example.se_project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return new HashMap<>() {{
            put("info", passengerService.addPassenger(name, identification, phone, id));
        }};


    }

    @PostMapping("/updatePassengers/{id}")
    public Map<String, Boolean> updatePassenger(@PathVariable String id,
                                                @RequestParam(value = "oldidentification") String oldidentification,
                                                @RequestParam(value = "newname", required = false, defaultValue = "") String newname,
                                                @RequestParam(value = "newidentification", required = false, defaultValue = "") String newidentification,
                                                @RequestParam(value = "newphone", required = false, defaultValue = "") String newphone) {

//        boolean result1=true,result2=true,result3=true;
//        if(!Objects.equals(newname, "")){
//            result1 = passengerService.updatePassengerName(id,oldidentification,newname);
//        }
//        if(!Objects.equals(newidentification, "")){
//            result2 = passengerService.updatePassengerIdentification(id,oldidentification,newidentification);
//        }
//        if(!Objects.equals(newphone, "")){
//            result3 = passengerService.updatePassengerPhone(id,oldidentification,newphone);
//        }
//
//        boolean finalResult = result1;
//        boolean finalResult1 = result2;
//        boolean finalResult2 = result3;
//        return new HashMap<>() {{
//            put("info", finalResult && finalResult1 && finalResult2);
//        }};
        if (newname.isEmpty()) newname = null;
        if (newidentification.isEmpty()) newidentification = null;
        if (newphone.isEmpty()) newphone = null;
        String finalNewname = newname;
        String finalNewidentification = newidentification;
        String finalNewphone = newphone;
        return new HashMap<>() {{
            put("info", passengerService.updatePassenger(id, oldidentification, finalNewname, finalNewidentification, finalNewphone));
        }};
    }

    @PostMapping("/passengersInfo/{id}")
    public Map<String, Object> showPassengerInfo(@PathVariable String id) {
        return new HashMap<>() {{
            put("passenger", passengerService.showPassengerInfo(id));
        }};
    }

    @DeleteMapping("/deletePassengers/{id}")
    public Map<String, Boolean> deletePassenger(@PathVariable String id,
                                                @RequestParam String name,
                                                @RequestParam String identification) {
        return new HashMap<>() {{
            put("info", passengerService.deletePassenger(id, name, identification));
        }};
    }
}

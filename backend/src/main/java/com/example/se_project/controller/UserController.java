package com.example.se_project.controller;

import com.example.se_project.entity.User;
import com.example.se_project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private User user;

    @PostMapping(value = "/register")
    public Map<String, Object> userRegister(@RequestBody Map<String, String> registerMap) {
        String id = registerMap.get("id");
        String name = registerMap.get("name");
        String password = registerMap.get("password");
        String email = registerMap.get("email");
        Map<String, Object> map = new HashMap<>();

        System.out.println(id);

        if (userService.findUserById(id) > 0) {
            map.put("message", "用户已注册");
        } else {
            userService.registerUser(id, name, password, email);
            map.put("message", "注册成功");
        }
        return map;
    }

    @GetMapping("/users/{id}")
    public Map<String, Object> showUserInfo(@PathVariable String id) {
        User showuser = userService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("id",showuser.getId());
        map.put("name", showuser.getName());
        map.put("email",showuser.getEmail());
        map.put("password",showuser.getPassword());
        return map;
    }

    @PostMapping("/login")
    public Map<String, Object> userLogin(@RequestBody Map<String, String> loginMap){
        String id = loginMap.get("id");
        String password = loginMap.get("password");
        Map<String, Object> map = new HashMap<>();
        if(userService.findUserById(id) == 0){
            map.put("message","用户未注册");
        }
        else{
            User loginuser = userService.login(id, password);

            if (loginuser != null) {
                map.put("id", loginuser.getId());
                map.put("name", loginuser.getName());
                map.put("email", loginuser.getEmail());
            } else {
                map.put("message", "密码错误");
            }
        }
        return map;
    }
}

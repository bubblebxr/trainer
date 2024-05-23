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

        //System.out.println(id);
        // 校验身份证号
        if (!id.matches("\\d{17}[\\dX]")) {
            map.put("result", false);
            map.put("reason", "身份证号格式错误");
            return map;
        }

        // 校验email格式
        if (!email.matches("\\w+@\\w+\\.\\w+")) {
            map.put("result", false);
            map.put("reason", "email格式错误");
            return map;
        }

        // 校验密码格式（密码要求：至少8个字符，包含数字和字母）
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")) {
            map.put("result", false);
            map.put("reason", "密码格式错误(密码要求：至少8个字符，包含数字和字母)");
            return map;
        }
        if (userService.findUserById(id) > 0) {
            map.put("result", false);
            map.put("reason", "用户已注册");
        } else {
            userService.registerUser(id, name, password, email);
            map.put("result", true);
            map.put("reason", "注册成功");
        }
        return map;
    }

    @GetMapping("/users/{id}")
    public Map<String, Object> showUserInfo(@PathVariable String id) {
        User showuser = userService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("id", showuser.getId());
        map.put("name", showuser.getName());
        map.put("email", showuser.getEmail());
        map.put("password", showuser.getPassword());
        return map;
    }

    @PostMapping("/login")
    public Map<String, Object> userLogin(@RequestBody Map<String, String> loginMap) {
        String id = loginMap.get("id");
        String password = loginMap.get("password");
        Map<String, Object> map = new HashMap<>();
        if (userService.findUserById(id) == 0) {
            //map.put("message","用户未注册");
            map.put("result", false);
            map.put("email", null);
            map.put("name", null);
        } else {
            User loginuser = userService.login(id, password);

            if (loginuser != null) {
                //map.put("id", loginuser.getId());
                map.put("email", loginuser.getEmail());
                map.put("name", loginuser.getName());
            } else {
                //map.put("message", "密码错误");
                map.put("result", false);
                map.put("email", null);
                map.put("name", null);
            }
        }
        return map;
    }
}

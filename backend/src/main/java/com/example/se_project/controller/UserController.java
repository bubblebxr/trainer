package com.example.se_project.controller;

import com.example.se_project.entity.User;
import com.example.se_project.service.IUserService;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    public Map<String, Object> userRegister(@RequestBody Map<String, Object> registerMap) {
        String id = registerMap.get("id").toString();
        String name = registerMap.get("name").toString();
        String password = registerMap.get("password").toString();
        String email = registerMap.get("email").toString();
        Integer time = Integer.parseInt(registerMap.get("time").toString());
        Map<String, Object> map = new HashMap<>();

        //System.out.println(id);
        // 校验身份证号
        if (!id.matches("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$")) {
            map.put("result", false);
            map.put("reason", "身份证号格式错误");
            return map;
        }

        // 校验email格式
//        if (!email.matches("\\w+@\\w+\\.\\w+")) {
//            map.put("result", false);
//            map.put("reason", "email格式错误");
//            return map;
//        }


        if (!email.matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
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
            if(time==2){
                userService.registerUser(id, name, password, email);
            }
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
    public Map<String, Object> userLogin(@RequestBody Map<String, Object> loginMap) {
        String id = loginMap.get("id").toString();
        String password = loginMap.get("password").toString();
        Map<String, Object> map = new HashMap<>();
        if (userService.findUserById(id) == 0) {
            map.put("message", "用户未注册");
            map.put("result", false);
            map.put("email", null);
            map.put("name", null);
        } else {
            User loginuser = userService.login(id, password);

            if (loginuser != null) {
                //map.put("id", loginuser.getId());
                map.put("message", "登录成功");
                map.put("result", true);
                map.put("email", loginuser.getEmail());
                map.put("name", loginuser.getName());
            } else {
                map.put("message", "用户id或密码错误");
                map.put("result", false);
                map.put("email", null);
                map.put("name", null);
            }
        }
        return map;
    }

    @PostMapping("/updatepassword")
    public Map<String, Object> updatePassword(@RequestBody Map<String, Object> map) {
        String userId = map.get("id").toString();
        String newpassword = map.get("newpassword").toString();
        Integer res = userService.updatePassword(userId, newpassword);
        if (res == 0) {
            return new HashMap<>() {{
                put("result", false);
            }};
        } else {
            return new HashMap<>() {{
                put("result", true);
            }};
        }
    }
}

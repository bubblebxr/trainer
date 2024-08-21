package com.buaa.werwertrip.controller;

import com.buaa.werwertrip.entity.User;
import com.buaa.werwertrip.mapper.IUserMapper;
import com.buaa.werwertrip.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void userRegister() {
        // 用例1：用户已注册
        Map<String, Object> userDetails1 = new HashMap<>();
        userDetails1.put("id", "120102200001012345");
        userDetails1.put("name", "陆");
        userDetails1.put("password", "123456AAA");
        userDetails1.put("email", "123456@qq.com");
        userDetails1.put("time","2");
        Assertions.assertFalse((Boolean)(userController.userRegister(userDetails1).get("result")));

        // 用例2：身份证号格式错误
        Map<String, Object> userDetails2 = new HashMap<>();
        userDetails2.put("id", "12010220000101");
        userDetails2.put("name", "陆");
        userDetails2.put("password", "123456AAA");
        userDetails2.put("email", "123456@qq.com");
        userDetails2.put("time","2");
        Assertions.assertFalse((Boolean)(userController.userRegister(userDetails2).get("result")));

        // 用例3：密码格式错误
        Map<String, Object> userDetails3 = new HashMap<>();
        userDetails3.put("id", "120102200001012345");
        userDetails3.put("name", "陆");
        userDetails3.put("password", "123456");
        userDetails3.put("email", "123456@qq.com");
        userDetails3.put("time","2");
        Assertions.assertFalse((Boolean)(userController.userRegister(userDetails3).get("result")));

        // 用例4：验证码错误
        Map<String, Object> userDetails4 = new HashMap<>();
        userDetails4.put("id", "120102200001012345");
        userDetails4.put("name", "陆");
        userDetails4.put("password", "123456AAA");
        userDetails4.put("email", "123456@qq.com");
        userDetails4.put("time","1");
        Assertions.assertFalse((Boolean)(userController.userRegister(userDetails4).get("result")));

        // 用例5：成功
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", "90123419860118123X");
        userDetails.put("name", "陆");
        userDetails.put("password", "123456AAA");
        userDetails.put("email", "3181577132@qq.com");
        userDetails.put("time","2");
        Assertions.assertTrue((Boolean)(userController.userRegister(userDetails).get("result")));
    }

    @Test
    void showUserInfo() {
        Map<String,Object> userInfo = userController.showUserInfo("102102200802100328");
        Assertions.assertNotNull(userInfo);
    }

    @Test
    void userLogin() {
        // 用例1：未注册
        Map<String, Object> userLoginMap1 = new HashMap<>();
        userLoginMap1.put("id", "100102200001012345");
        userLoginMap1.put("password", "123456AAA");
        Assertions.assertFalse((Boolean)(userController.userLogin(userLoginMap1).get("result")));

        // 用例2：密码错误
        Map<String, Object> userLoginMap2 = new HashMap<>();
        userLoginMap2.put("id", "120102200001012345");
        userLoginMap2.put("password", "123456AAA");
        Assertions.assertFalse((Boolean)(userController.userLogin(userLoginMap2).get("result")));

        // 用例3：成功登录
        Map<String, Object> userLoginMap3 = new HashMap<>();
        userLoginMap3.put("id", "89012319991209145X");
        userLoginMap3.put("password", "123456AAA");
        Assertions.assertTrue((Boolean)(userController.userLogin(userLoginMap3).get("result")));
    }

    @Test
    void updatePassword() {
        Map<String, Object> updatePasswordMap = new HashMap<>();
        updatePasswordMap.put("id","90123419860118123X");
        updatePasswordMap.put("newpassword","123456BBB");
        Assertions.assertTrue((Boolean)(userController.updatePassword(updatePasswordMap).get("result")));
    }

    @Test
    void forgetPassword() {
    }

    @Test
    void idCodeByEmail() {
    }
}
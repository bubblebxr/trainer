package com.buaa.werwertrip.mapper;

import com.buaa.werwertrip.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IUserMapperTest {

    @Autowired
    private IUserMapper userMapper;

    @Test
    void registerUser() {
        //Boolean register01 = userMapper.registerUser("102102200802100324","陆","123456AAA","123456@qq.com");
    }

    @Test
    void findUserById() {
        Integer has01 = userMapper.findUserById("102102200802100324");
        Assertions.assertEquals(1,has01);
        Integer has02 = userMapper.findUserById("102102200802100314");
        Assertions.assertEquals(1,has02);
    }

    @Test
    void findById() {
        User userinfo = userMapper.findById("102102200802100324");
        System.out.println(userinfo);
        Assertions.assertEquals("卜欣然",userinfo.getName());
    }

    @Test
    void login() {
    }

    @Test
    void getUserSalt() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void getEmail() {
    }
}
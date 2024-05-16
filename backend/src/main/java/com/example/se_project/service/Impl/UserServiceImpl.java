package com.example.se_project.service.Impl;

import com.example.se_project.entity.User;
import com.example.se_project.mapper.UserMapper;
import com.example.se_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int findUserById(String id) {
        return userMapper.findUserById(id);
    }


    @Override
    public User findById(String id){
        return userMapper.findById(id);
    }

    @Override
    public User registerUser(String id, String name, String password, String email) {
        String salt = "hjgoasjgajg165416565";
        //String DBPass = MD5Util.formPassToDBPass(password, salt);
        String DBPass = password;
        userMapper.registerUser(id, name, DBPass, email,salt);
        return userMapper.findById(id);
    }
}




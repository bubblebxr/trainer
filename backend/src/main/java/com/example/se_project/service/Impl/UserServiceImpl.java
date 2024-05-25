package com.example.se_project.service.Impl;

import com.example.se_project.entity.User;
import com.example.se_project.mapper.IUserMapper;
import com.example.se_project.service.IUserService;
import com.example.se_project.util.SaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.se_project.util.MD5Util;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

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
        String salt = SaltGenerator.generateSalt();
        String DBPass = MD5Util.md5(password, salt);
        //String DBPass = password;
        userMapper.registerUser(id, name, DBPass, email,salt);
        return userMapper.findById(id);
    }

    @Override
    public User login(String id, String password){
        String salt = userMapper.getUserSalt(id);
        String DBPass = MD5Util.md5(password, salt);
        return userMapper.login(id,DBPass);
    }

    @Override
    public boolean updatePassword(String userId,String newpassword){
        return userMapper.updatePassword(userId,newpassword);
    }

    @Override
    public String getEmail(String userId){
        return userMapper.getEmail(userId);
    }
}




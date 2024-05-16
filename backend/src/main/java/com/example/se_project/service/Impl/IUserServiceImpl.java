package com.example.se_project.service.Impl;

import com.example.se_project.entity.User;
import com.example.se_project.mapper.IUserMapper;
import com.example.se_project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.se_project.util.MD5Util;

@Service("userService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper IUserMapper;

    @Override
    public int findUserById(String id) {
        return IUserMapper.findUserById(id);
    }


    @Override
    public User findById(String id){
        return IUserMapper.findById(id);
    }

    @Override
    public User registerUser(String id, String name, String password, String email) {
        String salt = "hjgoasjgajg165416565";
        String DBPass = MD5Util.md5(password, salt);
        //String DBPass = password;
        IUserMapper.registerUser(id, name, DBPass, email,salt);
        return IUserMapper.findById(id);
    }

    @Override
    public User login(String id, String password){
        String salt = "hjgoasjgajg165416565";
        String DBPass = MD5Util.md5(password, salt);
        return IUserMapper.login(id,DBPass);

    }
}




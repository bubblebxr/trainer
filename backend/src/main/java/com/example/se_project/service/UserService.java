package com.example.se_project.service;

import com.example.se_project.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface UserService {
    public int findUserById(String id);

    public User registerUser(String id, String name, String password,String email);

    public User findById(String id);

    public User login(String id, String password);

}

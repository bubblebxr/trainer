package com.example.se_project.service;

import com.example.se_project.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {
    public int findUserById(String id);

    public User registerUser(String id, String name, String password,String email);

    public User findById(String id);

    public User login(String id, String password);

    boolean updatePassword(String userId,String newpassword);
    String getEmail(String userId);

}

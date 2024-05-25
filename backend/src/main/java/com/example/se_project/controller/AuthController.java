package com.example.se_project.controller;

import com.example.se_project.service.Impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

//    @Autowired
//    private EmailService emailService;
//
//    private Map<String, String> verificationCodes = new HashMap<>();
//
//    @PostMapping("/emailCode")
//    public String register(@RequestParam String email) {
//        // 生成验证码
//        String code = emailService.generateVerificationCode();
//
//        // 保存验证码 (在实际项目中，应该将其存储在数据库中)
//        verificationCodes.put(email, code);
//
////        try {
////            // 发送验证码邮件
////            emailService.sendVerificationEmail(email, code);
////        } catch (MessagingException e) {
////            return "Failed to send verification email";
////        }
//
//        return "Verification email sent successfully";
//    }
//
//    @PostMapping("/verify")
//    public String verify(@RequestParam String email, @RequestParam String code) {
//        // 检查验证码
//        if (verificationCodes.containsKey(email) && verificationCodes.get(email).equals(code)) {
//            verificationCodes.remove(email); // 验证成功后移除验证码
//            return "Verification successful";
//        } else {
//            return "Invalid verification code";
//        }
//    }
}


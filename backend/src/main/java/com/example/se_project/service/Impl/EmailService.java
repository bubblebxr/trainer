package com.example.se_project.service.Impl;

import com.example.se_project.entity.VerificationCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {

//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private VerificationCodeRepository verificationCodeRepository;
//
//    public String generateVerificationCode() {
//        Random random = new Random();
//        int code = 100000 + random.nextInt(900000); // 生成6位随机验证码
//        return String.valueOf(code);
//    }
//
//    public void sendVerificationEmail(String to) throws MessagingException {
//        String code = generateVerificationCode();
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expiresAt = now.plusMinutes(10); // 设置验证码有效期为10分钟
//
//        verificationCodeRepository.findByEmail(to)
//                .ifPresent(verificationCode -> verificationCodeRepository.delete(verificationCode));
//
//        VerificationCode verificationCode = new VerificationCode();
//        verificationCode.setEmail(to);
//        verificationCode.setCode(code);
//        verificationCode.setGeneratedAt(now);
//        verificationCode.setExpiresAt(expiresAt);
//
//        // 保存验证码到数据库
//        verificationCodeRepository.save(verificationCode);
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject("Email Verification Code");
//        helper.setText("Your verification code is: " + code, true);
//
//        mailSender.send(message);
//    }
//
//    public boolean verifyCode(String email, String code) {
//        Optional<VerificationCode> optionalVerificationCode = verificationCodeRepository.findByEmail(email);
//        if (optionalVerificationCode.isPresent()) {
//            VerificationCode verificationCode = optionalVerificationCode.get();
//            if (verificationCode.getCode().equals(code) && verificationCode.getExpiresAt().isAfter(LocalDateTime.now())) {
//                // 验证成功后删除验证码
//                verificationCodeRepository.delete(verificationCode);
//                return true;
//            }
//        }
//        return false;
//    }
}
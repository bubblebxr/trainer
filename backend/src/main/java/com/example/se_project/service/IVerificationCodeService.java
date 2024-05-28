package com.example.se_project.service;

import com.example.se_project.entity.VerificationCode;
import org.springframework.stereotype.Component;

@Component
public interface IVerificationCodeService {
    public VerificationCode getVerificationCode(String email);

    public void addVerificationCode(VerificationCode code);
}

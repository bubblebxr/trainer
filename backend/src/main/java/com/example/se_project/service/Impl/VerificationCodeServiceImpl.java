package com.example.se_project.service.Impl;

import com.example.se_project.entity.VerificationCode;
import com.example.se_project.mapper.IVerificationCodeMapper;
import com.example.se_project.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("verificationCodeService")
public class VerificationCodeServiceImpl implements IVerificationCodeService {

    @Autowired
    private IVerificationCodeMapper verificationCodeMapper;

    @Override
    public VerificationCode getVerificationCode(String email) {
        return verificationCodeMapper.getVerificationCode(email);
    }

    @Override
    public void addVerificationCode(VerificationCode code) {
        verificationCodeMapper.addVerificationCode(code);
    }
}

package com.example.se_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.se_project.entity.VerificationCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IVerificationCodeMapper extends BaseMapper<VerificationCode> {
    VerificationCode getVerificationCode(String email);

    void addVerificationCode(VerificationCode code);
}

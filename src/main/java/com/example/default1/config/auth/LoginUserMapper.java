package com.example.default1.config.auth;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginUserMapper {
    LoginUser selectLoginUserByLoginId(String loginId);
}

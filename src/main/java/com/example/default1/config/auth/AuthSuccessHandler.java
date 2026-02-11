package com.example.default1.config.auth;

import java.io.IOException;

import com.example.default1.module.user.converter.UserConverter;
import com.example.default1.module.user.dto.UserDTO;
import com.example.default1.module.user.mapper.UserMapper;
import com.example.default1.base.utils.CommonUtils;
import com.example.default1.base.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserConverter userConverter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserDTO dto = userMapper.findById(SessionUtils.getId());
        dto.setPassword("[hidden]");
        CommonUtils.responseSuccess(userConverter.fromDto(dto), response);
    }
}

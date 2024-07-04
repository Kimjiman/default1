package com.example.default1.config.auth;

import java.io.IOException;

import com.example.default1.module.user.mapper.UserMapper;
import com.example.default1.module.user.model.User;
import com.example.default1.utils.CommonUtil;
import com.example.default1.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        User user = userMapper.findById(SessionUtil.getUserId());
        user.setPassword("[hidden]");
        CommonUtil.responseSuccess(user, response);
    }
}

package com.example.default1.base.security;

import java.io.IOException;

import com.example.default1.module.user.model.User;
import com.example.default1.module.user.repository.UserRepository;
import com.example.default1.base.utils.CommonUtils;
import com.example.default1.base.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        User user = userRepository.findById(SessionUtils.getId()).orElse(null);
        if (user != null) {
            user.setPassword("[hidden]");
        }
        CommonUtils.responseSuccess(user, response);
    }
}

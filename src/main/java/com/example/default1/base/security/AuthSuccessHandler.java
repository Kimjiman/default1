package com.example.default1.base.security;

import com.example.default1.base.utils.CommonUtils;
import com.example.default1.base.utils.SessionUtils;
import com.example.default1.module.user.entity.User;
import com.example.default1.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
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

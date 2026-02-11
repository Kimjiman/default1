package com.example.default1.module.user.controller;

import com.example.default1.base.exception.CustomException;
import com.example.default1.base.security.jwt.JwtTokenInfo;
import com.example.default1.base.security.jwt.JwtTokenProvider;
import com.example.default1.base.utils.SessionUtils;
import com.example.default1.base.utils.StringUtils;
import com.example.default1.module.user.model.User;
import com.example.default1.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public JwtTokenInfo login(@RequestBody User user) {
        String loginId = user.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            throw new CustomException(2001, "아이디를 입력해주세요.");
        }

        String password = user.getPassword();
        if (StringUtils.isBlank(password)) {
            throw new CustomException(2002, "비밀번호를 입력해주세요.");
        }

        return userService.login(loginId, password);
    }

    @PostMapping("/logout")
    public void logout() {
        jwtTokenProvider.removeRefreshToken(SessionUtils.getLoginId());
    }

    @PostMapping("/token")
    public JwtTokenInfo accessToken(@RequestBody JwtTokenInfo jwtTokenInfo) {
        return jwtTokenProvider.createAccessToken(jwtTokenInfo);
    }


    @PostMapping("/test")
    public void test() {
        log.info("loginId: {}", SessionUtils.getLoginId());
        log.info("roleList: {}", SessionUtils.getRoleList());
    }
}



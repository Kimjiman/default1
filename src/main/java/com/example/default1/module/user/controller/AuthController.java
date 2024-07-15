package com.example.default1.module.user.controller;

import com.example.default1.base.jwt.JwtTokenInfo;
import com.example.default1.base.jwt.JwtTokenProvider;
import com.example.default1.exception.CustomException;
import com.example.default1.module.user.model.User;
import com.example.default1.module.user.service.UserService;
import com.example.default1.utils.JwtUtils;
import com.example.default1.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public JwtTokenInfo login(@RequestBody User user) {
        String loginId = user.getLoginId();

        if (StringUtils.isBlank(user.getLoginId())) {
            throw new CustomException(2001, "아이디를 입력해주세요.");
        }

        String password = user.getPassword();
        if (StringUtils.isBlank(user.getLoginId())) {
            throw new CustomException(2002, "비밀번호를 입력해주세요.");
        }

        return userService.login(loginId, password);
    }

    @PostMapping("/logout")
    public void logout() {
        jwtTokenProvider.removeRefreshToken(JwtUtils.getLoginId());
    }

    @PostMapping("/token")
    public JwtTokenInfo accessToken(@RequestBody JwtTokenInfo jwtTokenInfo) {
        return jwtTokenProvider.createAccessToken(jwtTokenInfo);
    }


    @PostMapping("/test")
    public void test() {
        log.info("loginId: {}", JwtUtils.getLoginId());
        log.info("roleList: {}", JwtUtils.getRoleList());
    }
}



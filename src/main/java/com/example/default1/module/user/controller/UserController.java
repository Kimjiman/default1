package com.example.default1.module.user.controller;

import com.example.default1.base.jwt.TokenInfo;
import com.example.default1.base.redis.RedisObject;
import com.example.default1.exception.CustomException;
import com.example.default1.module.user.model.User;
import com.example.default1.module.user.service.UserService;
import com.example.default1.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public void createUser(User user) {
        userService.createUser(user);
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody User user) {
        log.info("login start userInfo: {}", user);
        String loginId = user.getLoginId();
        if(StringUtils.isBlank(user.getLoginId())) {
            throw new CustomException(2001, "아이디를 입력해주세요.");
        }
        String password = user.getPassword();
        if(StringUtils.isBlank(user.getLoginId())) {
            throw new CustomException(2002, "비밀번호를 입력해주세요.");
        }
        
        return userService.login(loginId, password);
    }

    @PostMapping("/accessToken")
    public TokenInfo accessToken(@RequestBody TokenInfo tokenInfo) {
        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(userService.generateAccessToken(tokenInfo.getRefreshToken()))
                .build();
    }

    @PostMapping("/test")
    public void test() {
    }
}

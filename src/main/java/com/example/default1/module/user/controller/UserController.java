package com.example.default1.module.user.controller;

import com.example.default1.base.jwt.TokenInfo;
import com.example.default1.base.redis.RedisObject;
import com.example.default1.module.user.model.User;
import com.example.default1.module.user.service.UserService;
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
        String password = user.getPassword();
        return userService.login(loginId, password);
    }

    @PostMapping("/accessToken/{refreshToken}")
    public TokenInfo accessToken(@PathVariable String refreshToken) {
        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(userService.generateAccessToken(refreshToken))
                .build();
    }
}

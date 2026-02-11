package com.example.default1.module.user.controller;

import com.example.default1.base.security.jwt.JwtTokenInfo;
import com.example.default1.base.utils.SessionUtils;
import com.example.default1.module.user.facade.UserFacade;
import com.example.default1.module.user.model.UserModel;
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
    private final UserFacade userFacade;

    @PostMapping("/login")
    public JwtTokenInfo login(@RequestBody UserModel userModel) {
        return userFacade.login(userModel);
    }

    @PostMapping("/logout")
    public void logout() {
        userFacade.logout();
    }

    @PostMapping("/token")
    public JwtTokenInfo accessToken(@RequestBody JwtTokenInfo jwtTokenInfo) {
        return userFacade.accessToken(jwtTokenInfo);
    }

    @PostMapping("/test")
    public void test() {
        log.info("loginId: {}", SessionUtils.getLoginId());
        log.info("roleList: {}", SessionUtils.getRoleList());
    }
}

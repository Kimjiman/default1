package com.example.basicarch.module.user.facade;

import com.example.basicarch.base.annotation.Facade;
import com.example.basicarch.base.exception.SystemErrorCode;
import com.example.basicarch.base.exception.ToyAssert;
import com.example.basicarch.base.security.jwt.JwtTokenInfo;
import com.example.basicarch.base.security.jwt.JwtTokenService;
import com.example.basicarch.base.utils.SessionUtils;
import com.example.basicarch.module.user.model.UserModel;
import com.example.basicarch.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Facade
@RequiredArgsConstructor
@Slf4j
public class AuthFacade {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    public JwtTokenInfo login(UserModel userModel) {
        ToyAssert.notBlank(userModel.getLoginId(), SystemErrorCode.REQUIRED, "아이디를 입력해주세요.");
        ToyAssert.notBlank(userModel.getPassword(), SystemErrorCode.REQUIRED, "비밀번호를 입력해주세요.");
        return userService.login(userModel.getLoginId(), userModel.getPassword());
    }

    public void logout() {
        jwtTokenService.removeRefreshToken(SessionUtils.getLoginId());
    }

    public JwtTokenInfo issueAccessToken(JwtTokenInfo jwtTokenInfo) {
        return jwtTokenService.issueAccessToken(jwtTokenInfo);
    }
}

package com.example.default1.module.user.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.base.exception.SystemErrorCode;
import com.example.default1.base.exception.ToyAssert;
import com.example.default1.base.security.jwt.JwtTokenInfo;
import com.example.default1.base.security.jwt.JwtTokenService;
import com.example.default1.base.utils.SessionUtils;
import com.example.default1.module.user.converter.UserConverter;
import com.example.default1.module.user.model.UserModel;
import com.example.default1.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Facade
@RequiredArgsConstructor
@Slf4j
public class UserFacade {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final UserConverter userConverter;

    public JwtTokenInfo login(UserModel userModel) {
        ToyAssert.notBlank(userModel.getLoginId(), SystemErrorCode.REQUIRED, "아이디를 입력해주세요.");
        ToyAssert.notBlank(userModel.getPassword(), SystemErrorCode.REQUIRED, "비밀번호를 입력해주세요.");
        return userService.login(userModel.getLoginId(), userModel.getPassword());
    }

    public void logout() {
        jwtTokenService.removeRefreshToken(SessionUtils.getLoginId());
    }

    public JwtTokenInfo accessToken(JwtTokenInfo jwtTokenInfo) {
        return jwtTokenService.refreshAccessToken(jwtTokenInfo);
    }

    public void saveUser(UserModel userModel) {
        ToyAssert.notBlank(userModel.getLoginId(), SystemErrorCode.REQUIRED, "로그인 아이디를 입력해주세요.");
        if (userModel.getId() == null) {
            ToyAssert.notBlank(userModel.getPassword(), SystemErrorCode.REQUIRED, "패스워드를 입력해주세요.");
        }
        userService.save(userConverter.toEntity(userModel));
    }
}

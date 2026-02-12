package com.example.default1.module.user.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.base.exception.CustomException;
import com.example.default1.base.security.jwt.JwtTokenInfo;
import com.example.default1.base.security.jwt.JwtTokenService;
import com.example.default1.base.utils.SessionUtils;
import com.example.default1.base.utils.StringUtils;
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

    public JwtTokenInfo login(UserModel userModel) {
        String loginId = userModel.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            throw new CustomException(2001, "아이디를 입력해주세요.");
        }

        String password = userModel.getPassword();
        if (StringUtils.isBlank(password)) {
            throw new CustomException(2002, "비밀번호를 입력해주세요.");
        }

        return userService.login(loginId, password);
    }

    public void logout() {
        jwtTokenService.removeRefreshToken(SessionUtils.getLoginId());
    }

    public JwtTokenInfo accessToken(JwtTokenInfo jwtTokenInfo) {
        return jwtTokenService.refreshAccessToken(jwtTokenInfo);
    }
}

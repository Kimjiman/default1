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
    private final UserConverter userConverter;

    public void createUser(UserModel userModel) {
        ToyAssert.notBlank(userModel.getLoginId(), SystemErrorCode.REQUIRED, "로그인 아이디를 입력해주세요.");
        if (userModel.getId() == null) {
            ToyAssert.notBlank(userModel.getPassword(), SystemErrorCode.REQUIRED, "패스워드를 입력해주세요.");
        }
        ToyAssert.notBlank(userModel.getName(), SystemErrorCode.REQUIRED, "이름을 입력해주세요.");
        userService.save(userConverter.toEntity(userModel));
    }

    public void updateUser(UserModel userModel) {
        ToyAssert.notBlank(userModel.getLoginId(), SystemErrorCode.REQUIRED, "로그인 아이디를 입력해주세요.");
        ToyAssert.notBlank(userModel.getName(), SystemErrorCode.REQUIRED, "이름을 입력해주세요.");
        userService.update(userConverter.toEntity(userModel));
    }
}

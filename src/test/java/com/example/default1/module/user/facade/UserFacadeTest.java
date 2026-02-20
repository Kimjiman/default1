package com.example.default1.module.user.facade;

import com.example.default1.base.constants.YN;
import com.example.default1.base.exception.CustomException;
import com.example.default1.module.user.converter.UserConverter;
import com.example.default1.module.user.model.UserModel;
import com.example.default1.module.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.example.default1.module.user.facade
 * fileName       : UserFacadeTest
 * author         : KIM JIMAN
 * date           : 26. 2. 20. 금요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 26. 2. 20.     KIM JIMAN      First Commit
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserFacade 테스트")
class UserFacadeTest {
    @Mock
    private UserService userService;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserFacade userFacade;

    @Test
    @DisplayName("User 생성, loginId없이 생성할경우 에러발생")
    void user_create_fail() {
        UserModel userModel = UserModel.builder()
                .password("pass1234")
                .name("testUser")
                .useYn(YN.Y)
                .roleList(List.of("USR"))
                .build();

        assertThrows(CustomException.class, () -> userFacade.createUser(userModel));
    }


    @Test
    @DisplayName("User 수정, loginId없이 수정할경우 에러발생")
    void user_update_fail() {
        UserModel userModel = UserModel.builder()
                .password("pass1234")
                .name("testUser")
                .useYn(YN.N)
                .roleList(List.of("USR"))
                .build();

        assertThrows(CustomException.class, () -> userFacade.updateUser(userModel));
    }

    @Test
    @DisplayName("User 생성")
    void user_create_suc() {
        UserModel userModel = UserModel.builder()
                .loginId("testUser")
                .password("pass1234")
                .name("testUser")
                .useYn(YN.Y)
                .roleList(List.of("USR"))
                .build();

        userFacade.createUser(userModel);
        verify(userService).save(any());
    }
}

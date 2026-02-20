package com.example.default1.module.user.facade;

import com.example.default1.base.exception.CustomException;
import com.example.default1.base.security.jwt.JwtTokenInfo;
import com.example.default1.base.security.jwt.JwtTokenService;
import com.example.default1.module.user.model.UserModel;
import com.example.default1.module.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.example.default1.module.user.facade
 * fileName       : AuthFacadeTest
 * author         : KIM JIMAN
 * date           : 26. 2. 20. 금요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 26. 2. 20.     KIM JIMAN      First Commit
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthFacade 테스트")
class AuthFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private AuthFacade authFacade;

    @Test
    @DisplayName("로그인 성공 시 JwtTokenInfo 반환")
    void login_success() {
        UserModel userModel = UserModel.builder()
                .loginId("testUser")
                .password("pass1234")
                .build();

        JwtTokenInfo expected = JwtTokenInfo.builder()
                .grantType("Bearer")
                .accessToken("access-token-value")
                .refreshToken("refresh-token-value")
                .build();

        given(userService.login("testUser", "pass1234")).willReturn(expected);

        JwtTokenInfo result = authFacade.login(userModel);

        assertThat(result.getAccessToken()).isEqualTo("access-token-value");
        assertThat(result.getRefreshToken()).isEqualTo("refresh-token-value");
        assertThat(result.getGrantType()).isEqualTo("Bearer");

        verify(userService).login("testUser", "pass1234");
    }

    @Test
    @DisplayName("로그인 아이디가 null 이면 CustomException 발생")
    void login_id_null() {
        UserModel userModel = UserModel.builder()
                .loginId(null)
                .password("pass1234")
                .build();

        assertThrows(CustomException.class, () -> authFacade.login(userModel));

        verify(userService, never()).login(any(), any());
    }

    @Test
    @DisplayName("로그인 아이디가 빈 문자열이면 CustomException 발생")
    void login_id_empty() {
        UserModel userModel = UserModel.builder()
                .loginId("")
                .password("pass1234")
                .build();

        assertThrows(CustomException.class, () -> authFacade.login(userModel));

        verify(userService, never()).login(any(), any());
    }

    @Test
    @DisplayName("비밀번호가 null 이면 CustomException 발생")
    void login_password_null() {
        UserModel userModel = UserModel.builder()
                .loginId("testUser")
                .password(null)
                .build();

        assertThrows(CustomException.class, () -> authFacade.login(userModel));

        verify(userService, never()).login(any(), any());
    }

    @Test
    @DisplayName("비밀번호가 빈 문자열이면 CustomException 발생")
    void login_password_empty() {
        UserModel userModel = UserModel.builder()
                .loginId("testUser")
                .password("")
                .build();

        assertThrows(CustomException.class, () -> authFacade.login(userModel));

        verify(userService, never()).login(any(), any());
    }


    @Test
    @DisplayName("AccessToken 재발급 성공")
    void issuingAccessToken() {
        JwtTokenInfo request = JwtTokenInfo.builder()
                .refreshToken("valid-refresh-token")
                .build();

        JwtTokenInfo expected = JwtTokenInfo.builder()
                .grantType("Bearer")
                .accessToken("new-access-token")
                .refreshToken("valid-refresh-token")
                .build();

        given(jwtTokenService.issueAccessToken(request)).willReturn(expected);

        JwtTokenInfo result = authFacade.issueAccessToken(request);

        assertThat(result.getAccessToken()).isEqualTo("new-access-token");
        verify(jwtTokenService).issueAccessToken(request);
    }
}

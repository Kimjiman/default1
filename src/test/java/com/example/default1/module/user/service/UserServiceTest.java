package com.example.default1.module.user.service;

import com.example.default1.base.security.jwt.JwtTokenService;
import com.example.default1.module.user.entity.User;
import com.example.default1.module.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.example.default1.module.user.service
 * fileName       : UserServiceTest
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
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Password 인코딩 성공")
    void password_encoder_suc() {
        User user = User.builder()
                .loginId("loginId")
                .password("pass1234")
                .name("testUser")
                .useYn("Y")
                .roleList(List.of("USR"))
                .build();

        given(passwordEncoder.encode(user.getPassword())).willReturn("encodedPassword");
        given(userRepository.save(any())).willReturn(user);

        userService.save(user);

        verify(passwordEncoder).encode("pass1234");
        verify(userRepository).save(any());

    }

    @Test
    @DisplayName("Password 인코딩 실패")
    void password_encoder_fail() {
        User user = User.builder()
                .loginId("loginId")
                .name("testUser")
                .useYn("Y")
                .roleList(List.of("USR"))
                .build();

        given(userRepository.save(any())).willReturn(user);

        userService.save(user);

        verify(passwordEncoder, never()).encode(any());
        verify(userRepository).save(any());
    }
}

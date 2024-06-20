package com.example.default1.module.user.service;

import com.example.default1.base.jwt.JwtTokenProvider;
import com.example.default1.base.jwt.TokenInfo;
import com.example.default1.exception.CustomException;
import com.example.default1.module.user.mapper.UserMapper;
import com.example.default1.module.user.model.User;
import com.example.default1.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenInfo login(String loginId, String password) {
        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        log.info("tokenInfo: {}", tokenInfo);
        return tokenInfo;
    }

    /**
     * refreshToken을 이용하여 accessToken 생성
     *
     * @param refreshToken
     * @return
     */
    public String generateAccessToken(String refreshToken) {
        return jwtTokenProvider.generateAccessToken(refreshToken);
    }

    public User selectUser(Long id) {
        return userMapper.findById(id);
    }

    public void createUser(User user) {
        if(StringUtils.isBlank(user.getLoginId())) {
            throw new CustomException(2001, "로그인 아이디를 입력해주세요.");
        }
        
        if(StringUtils.isBlank(user.getPassword())) {
            throw new CustomException(2001, "패스워드를 입력해주세요.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(Long id) {
        if(id == null) return;
        userMapper.deleteById(id);
    }

}

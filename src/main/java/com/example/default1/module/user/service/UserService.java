package com.example.default1.module.user.service;

import com.example.default1.base.jwt.JwtTokenProvider;
import com.example.default1.base.jwt.JwtTokenInfo;
import com.example.default1.base.service.BaseService;
import com.example.default1.exception.CustomException;
import com.example.default1.module.user.model.UserSearchParam;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements BaseService<User, UserSearchParam, Long> {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenInfo login(String loginId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createJwtTokenInfo(authentication);
    }

    @Override
    public boolean existsById(Long id) {
        return userMapper.findById(id) != null;
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public Long countAllBy(UserSearchParam param) {
        return 0L;
    }

    @Override
    public List<User> findAllBy(UserSearchParam param) {
        return List.of();
    }

    @Override
    public Long create(User user) {
        if (StringUtils.isBlank(user.getLoginId())) {
            throw new CustomException(2001, "로그인 아이디를 입력해주세요.");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            throw new CustomException(2001, "패스워드를 입력해주세요.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.create(user);
        return user.getId();
    }

    @Override
    public Long update(User user) {
        userMapper.update(user);
        return user.getId();
    }

    @Override
    public boolean removeById(Long id) {
        return userMapper.removeById(id) > 0;
    }
}

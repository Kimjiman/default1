package com.example.default1.module.user.service;

import com.example.default1.base.security.jwt.JwtTokenService;
import com.example.default1.base.security.jwt.JwtTokenInfo;
import com.example.default1.base.service.BaseService;
import com.example.default1.module.user.entity.User;
import com.example.default1.module.user.model.UserSearchParam;
import com.example.default1.module.user.repository.UserRepository;
import com.example.default1.base.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements BaseService<User, UserSearchParam, Long> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public JwtTokenInfo login(String loginId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return jwtTokenService.createJwtTokenInfo(authentication);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllBy(UserSearchParam param) {
        return userRepository.findAllBy(param);
    }

    @Override
    public User save(User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

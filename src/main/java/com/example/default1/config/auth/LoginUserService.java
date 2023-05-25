package com.example.default1.config.auth;

import com.example.default1.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginUserService implements UserDetailsService {
    @Autowired
    private LoginUserMapper loginUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = loginUserMapper.selectLoginUserByLoginId(username);
        if(loginUser != null) {
            loginUser.setRoleList(List.of("USER"));
        } else {
            throw new CustomException(2001, "가입되지 않은 사용자입니다.");
        }

        return new LoginUserDetails(loginUser);
    }
}

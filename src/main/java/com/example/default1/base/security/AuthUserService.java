package com.example.default1.base.security;

import com.example.default1.module.user.model.User;
import com.example.default1.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = new User();
        if (loginId.equals("admin")) {
            user.setLoginId(loginId);
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoleList(List.of("USR"));
        } else {
            throw new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다.");
        }

        return new AuthUserDetails(user);
    }
}

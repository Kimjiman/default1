package com.example.default1.config.auth;

import com.example.default1.module.user.converter.UserConverter;
import com.example.default1.module.user.mapper.UserMapper;
import com.example.default1.module.user.model.User;
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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//        User user = userConverter.fromDto(userMapper.findByLoginId(loginId));
//        if(authUser != null) {
//            /*
//            * TODO 회원 권한 코드테이블에서 가져와야 함.
//            * 메뉴 테이블 생성 및 해당 유저가 로그인 시 회원권한에 따른 접근 가능 메뉴에 대한 배열 필요
//            */
//            authUser.setRoleList(List.of("USR"));
//        } else {
//            throw new CustomException(2001, "가입되지 않은 사용자입니다.");
//        }

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

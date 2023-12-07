package com.example.default1.config.auth;

import com.example.default1.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginUserService implements UserDetailsService {
    private final LoginUserMapper loginUserMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        LoginUser loginUser = loginUserMapper.selectLoginUserByLoginId(loginId);
        if(loginUser != null) {
            /*
            * TODO 회원 권한 코드테이블에서 가져와야 함.
            * 메뉴 테이블 생성 및 해당 유저가 로그인 시 회원권한에 따른 접근 가능 메뉴에 대한 배열 필요
            */
            loginUser.setRoleList(List.of("USER"));
        } else {
            throw new CustomException(2001, "가입되지 않은 사용자입니다.");
        }

        return new LoginUserDetails(loginUser);
    }
}

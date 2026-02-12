package com.example.default1.base.security;

import com.example.default1.module.user.model.User;
import com.example.default1.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.example.default1.base.security
 * fileName       : AuthUserDetailsService
 * author         : KIM JIMAN
 * date           : 26. 2. 12. 목요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 26. 2. 12.     KIM JIMAN      First Commit
 */
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException(loginId));
        return new AuthUserDetails(user);
    }
}
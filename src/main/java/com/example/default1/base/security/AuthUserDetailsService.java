package com.example.default1.base.security;

import com.example.default1.module.user.entity.Role;
import com.example.default1.module.user.entity.User;
import com.example.default1.module.user.entity.UserRole;
import com.example.default1.module.user.repository.RoleRepository;
import com.example.default1.module.user.repository.UserRepository;
import com.example.default1.module.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException(loginId));

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

        if (userRoles.isEmpty()) {
            user.setRoleList(List.of("USR"));
        } else {
            List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            List<String> roleNames = roleRepository.findAllById(roleIds).stream()
                    .filter(role -> "Y".equals(role.getUseYn()))
                    .map(Role::getRoleName)
                    .collect(Collectors.toList());
            user.setRoleList(roleNames.isEmpty() ? List.of("USR") : roleNames);
        }

        return new AuthUserDetails(user);
    }
}

package com.example.default1.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class LoginUserDetails implements UserDetails {
    private final LoginUser loginUser;

    public LoginUserDetails(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return loginUser.getRoleList().stream()
                .map(it -> new SimpleGrantedAuthority("ROLE_" + it))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.loginUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.loginUser.getLoginId() == null ? "" : this.loginUser.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

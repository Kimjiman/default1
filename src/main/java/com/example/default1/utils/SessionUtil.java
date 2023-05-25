package com.example.default1.utils;

import com.example.default1.config.auth.LoginUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class SessionUtil {

    public static String getPrincipal() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return "GUS";
        }
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (obj instanceof UserDetails) {
            return ((UserDetails) obj).getUsername();
        } else {
            return "GUS";
        }
    }

    public static LoginUserDetails getUser() {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (obj.equals("anonymousUser")) {
            return null;
        } else {
            //암호화 전 패스워드
            //((AuthInfo)obj).getUser().setPassword(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
            return ((LoginUserDetails) obj);
        }
    }

    public static Long getUserId() {
        LoginUserDetails loginUserDetails = getUser();
        return loginUserDetails != null ? loginUserDetails.getLoginUser().getId() : null;
    }

    public static String getUserLoginId() {
        LoginUserDetails loginUserDetails = getUser();
        return loginUserDetails != null ? loginUserDetails.getUsername() : null;
    }

    public static List<String> getUserRoleList() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> roleList = new ArrayList<>();
        if(authorities.isEmpty()) {
            roleList.add("ANONYMOUS");
        } else {
            authorities.forEach(it -> roleList.add(it.getAuthority()));
        }
        return roleList;
    }
}
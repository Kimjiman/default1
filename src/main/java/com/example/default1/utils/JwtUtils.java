package com.example.default1.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * packageName    : com.example.default1.utils
 * fileName       : JwtUtils
 * author         : KIM JIMAN
 * date           : 24. 7. 4. 목요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 24. 7. 4.     KIM JIMAN      First Commit
 */
public class JwtUtils {
    public static String getLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static List<GrantedAuthority> getRoleList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authoritieList = authentication.getAuthorities();
        return new ArrayList<>(authoritieList);
    }
}

package com.example.basicarch.config.interceptor;

import com.example.basicarch.base.exception.CustomException;
import com.example.basicarch.base.exception.SystemErrorCode;
import com.example.basicarch.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleInterceptor implements HandlerInterceptor {
    private final MenuService menuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();

        List<String> requiredRoles = menuService.findRolesByUri(uri);

        // 메뉴에 등록되지 않은 URI는 접근 허용
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(SystemErrorCode.FORBIDDEN);
        }

        // ROLE_ 접두어 제거 후 비교
        List<String> userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
                .collect(Collectors.toList());

        boolean hasRole = requiredRoles.stream().anyMatch(userRoles::contains);

        if (!hasRole) {
            throw new CustomException(SystemErrorCode.FORBIDDEN);
        }

        return true;
    }
}

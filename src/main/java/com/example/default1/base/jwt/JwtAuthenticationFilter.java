package com.example.default1.base.jwt;

import com.example.default1.constants.UrlConstatns;
import com.example.default1.exception.CustomException;
import com.example.default1.utils.CollectionUtils;
import com.example.default1.utils.CommonUtils;
import com.example.default1.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        try {
            if (!isAllowedUrl(requestURI)) {
                String token = resolveToken(httpRequest);
                if (jwtTokenProvider.validateToken(token)) {
                    Authentication authentication = jwtTokenProvider.getAuthenticationByToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);
        } catch (CustomException ex) {
            CommonUtils.responseFail(ex.getStatus(), ex.getMessage(), (HttpServletResponse) response);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isAllowedUrl(String requestURI) {
        List<String> allowedUrlList = CollectionUtils.arrayToList(UrlConstatns.ALLOWED_URL());
        return allowedUrlList.stream()
                .anyMatch(it -> pathMatcher.match(it, requestURI));
    }
}

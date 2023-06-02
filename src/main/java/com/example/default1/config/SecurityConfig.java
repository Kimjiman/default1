package com.example.default1.config;

import javax.servlet.http.HttpServletResponse;

import com.example.default1.config.auth.LoginFailureHandler;
import com.example.default1.config.auth.LoginSuccessHandler;
import com.example.default1.config.auth.LoginUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;


@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    private final LoginUserService loginUserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain exceptionSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .requestMatchers((matchers) -> matchers.antMatchers("/static/**"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
                .requestCache().disable()
                .securityContext().disable()
                .sessionManagement().disable()
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ContentNegotiationStrategy contentNegotiationStrategy = http.getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }

        MediaTypeRequestMatcher preferredMatcher = new MediaTypeRequestMatcher(
                contentNegotiationStrategy,
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON,
                MediaType.MULTIPART_FORM_DATA
        );

        preferredMatcher.setUseEquals(true);

        return http
                .headers(header -> header
                        //X-Frame-Options 셋팅 , 크로스 사이트 스크립트 방지 해재 default 'DENY'
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorizeRequest -> authorizeRequest
                        .antMatchers(
                                "/login"
                                , "/join"
                                , "/user/join"
                                , "/test"
                                , "/test/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler())
                        .failureHandler(loginFailureHandler())
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .deleteCookies("JSESSIONID")
                        .deleteCookies("REMEMBER_ME_COOKIE")
                        .invalidateHttpSession(true)
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("REMEMBER_ME_KEY")
                        .rememberMeServices(tokenBasedRememberMeServices())
                )
                //첫번째 로그인 사용자는 로그아웃, 두번째 사용자 로그인 session-registry-alias : 접속자 정보보기
                /*.sessionManagement(sessionManagement -> sessionManagement
                    .maximumSessions(1)
                    .expiredUrl("/expireSession")
                    .sessionRegistry(sessionRegistry())
                )*/
                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(unauthorizeEntryPoint(), preferredMatcher)
                )
                .build();
    }

    @Bean
    public RememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices("REMEMBER_ME_KEY", loginUserService);
        tokenBasedRememberMeServices.setAlwaysRemember(true);    // 체크박스 클릭안해도 무조건 유지
        tokenBasedRememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 30);    // 30일
        tokenBasedRememberMeServices.setCookieName("REMEMBER_ME_COOKIE");
        return tokenBasedRememberMeServices;
    }

    @Bean
    protected AuthenticationEntryPoint unauthorizeEntryPoint() {
        return (req, res, ex) -> {
            log.error("unauthorized: session[{}] uri[{}] message[{}]", req.getSession().getId(), req.getRequestURI(), ex.getMessage());
            String msg = ex.getMessage();
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
        };
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
package com.example.default1.config;

import javax.servlet.http.HttpServletResponse;

import com.example.default1.base.jwt.JwtAuthenticationEntryPoint;
import com.example.default1.base.jwt.JwtAuthenticationFilter;
import com.example.default1.base.jwt.JwtTokenProvider;
import com.example.default1.config.auth.LoginFailureHandler;
import com.example.default1.config.auth.LoginSuccessHandler;
import com.example.default1.config.auth.LoginUserService;
import com.example.default1.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * static 밑의 하위 경로 매핑을 위해 1번순서로 보안필터적용
     * @param http
     * @return
     * @throws Exception
     */
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

    /**
     * 매핑 url과 다른 세팅을 위한용도로 2번째 순서로 적용
     * @param http
     * @return
     * @throws Exception
     */
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
                .cors()
                .and()
                .httpBasic().disable()
                .headers(header -> header
                        //X-Frame-Options 셋팅 , 크로스 사이트 스크립트 방지 해재 default 'DENY'
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeRequests(authorizeRequest -> authorizeRequest
                        .antMatchers(
                                "/login"
                                , "/join"
                                , "/user/join"
                                , "/user/login"
                                , "/test"
                                , "/test/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/user/login")
//                        .usernameParameter("loginId")
//                        .passwordParameter("password")
//                        .successHandler(loginSuccessHandler())
//                        .failureHandler(loginFailureHandler())
//                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .deleteCookies("JSESSIONID")
                        .deleteCookies("REMEMBER_ME_COOKIE")
                        .invalidateHttpSession(true)
                )
                //첫번째 로그인 사용자는 로그아웃, 두번째 사용자 로그인 session-registry-alias : 접속자 정보보기
                .sessionManagement(sessionManagement -> sessionManagement
                    .maximumSessions(1)
                    .expiredUrl("/expireSession")
                    .sessionRegistry(sessionRegistry())
                )
                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(unauthorizeEntryPoint(), preferredMatcher)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    protected AuthenticationEntryPoint unauthorizeEntryPoint() {
        return (req, res, ex) -> {
            log.error("unauthorized: session[{}] uri[{}] message[{}]", req.getSession().getId(), req.getRequestURI(), ex.getMessage());
            String msg = ex.getMessage();
            CommonUtil.responseFail(HttpServletResponse.SC_UNAUTHORIZED, msg, res);
//            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
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
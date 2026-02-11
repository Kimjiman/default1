package com.example.default1.config;

import com.example.default1.base.security.jwt.JwtAuthenticationEntryPoint;
import com.example.default1.base.security.jwt.JwtAuthenticationFilter;
import com.example.default1.base.security.jwt.JwtTokenProvider;
import com.example.default1.base.security.AuthFailureHandler;
import com.example.default1.base.security.AuthSuccessHandler;
import com.example.default1.base.constants.UrlConstatns;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
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
    @Value("${user.swagger.id}")
    private String userSwaggerId;
    @Value("${user.swagger.pw}")
    private String userSwaggerPw;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain endpointFilterChain(HttpSecurity http) throws Exception {
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
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeRequests(authorizeRequest -> authorizeRequest
                        .antMatchers(UrlConstatns.SWAGGER_URLS).permitAll()
                        .antMatchers(UrlConstatns.ALLOWED_URLS).permitAll()
                        .antMatchers(UrlConstatns.RESOURCE_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthSuccessHandler loginSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Bean
    public AuthFailureHandler loginFailureHandler() {
        return new AuthFailureHandler();
    }
}
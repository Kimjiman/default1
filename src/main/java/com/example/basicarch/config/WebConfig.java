package com.example.default1.config;

import com.example.default1.base.constants.UrlConstants;
import com.example.default1.base.converter.YnToEnumConverter;
import com.example.default1.config.interceptor.RoleInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final RoleInterceptor roleInterceptor;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new YnToEnumConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePatterns = new ArrayList<>();
        excludePatterns.addAll(Arrays.asList(UrlConstants.SWAGGER_URLS));
        excludePatterns.addAll(Arrays.asList(UrlConstants.ALLOWED_URLS));
        excludePatterns.addAll(Arrays.asList(UrlConstants.RESOURCE_URLS));

        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePatterns);
    }
}

package com.example.default1.config;

import com.example.default1.base.model.Response;
import com.example.default1.constants.UrlConstatns;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private static final String[] EXCLUDE_URL_PATTERNS = {
            "/v3/api-docs",
            "/swagger-ui"
    };

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return !Response.class.equals(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        String requestPath = request.getURI().getPath();
        for (String pattern : EXCLUDE_URL_PATTERNS) {
            log.info("pattern: {}", pattern);
            if (requestPath.startsWith(pattern)) {
                return body;
            }
        }

        return Response.success(body);
    }
}

package com.example.default1.utils;

import com.example.default1.base.model.Response;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class CommonUtil {
    private static final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    private static final MediaType jsonMimeType = MediaType.APPLICATION_JSON;

    public static <T> void responseSuccess(T obj, HttpServletResponse response) throws IOException {
        jsonConverter.write(Response.success(obj), jsonMimeType, new ServletServerHttpResponse(response));
    }

    public static void responseFail(Integer status, String message, HttpServletResponse response) throws IOException {
        jsonConverter.write(Response.fail(status, message), jsonMimeType, new ServletServerHttpResponse(response));
    }

    public static Cookie setCookie(String key, String value, int day, String path) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(day * 24 * 60 * 60);
        cookie.setPath(path);
        return cookie;
    }

    public static Cookie setCookie(String key, String value) {
        int day = 365;
        String path = "/";
        return setCookie(key, value, day, path);
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(key))
                    .findFirst();
        }
        return Optional.empty();
    }
}

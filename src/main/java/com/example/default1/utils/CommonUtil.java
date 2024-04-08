package com.example.default1.utils;

import com.example.default1.base.model.Response;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class CommonUtil {
    private static final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    private static final MediaType jsonMimeType = MediaType.APPLICATION_JSON;

    public static <T> void responseSuccess(T obj, HttpServletResponse response) throws IOException {
        jsonConverter.write(Response.success(obj), jsonMimeType, new ServletServerHttpResponse(response));
    }

    public static void responseFail(Integer status, String message, HttpServletResponse response) throws IOException {
        jsonConverter.write(Response.fail(status, message), jsonMimeType, new ServletServerHttpResponse(response));
    }

    public static void setCookie(HttpServletResponse response, String key, String value, int day, String path) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(day * 24 * 60 * 60);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public static void setCookie(HttpServletResponse response, String key, String value) {
        int day = 365;
        String path = "/";
        setCookie(response, key, value, day, path);
    }

    public static void deleteCookie(HttpServletResponse response, String key) {
        int day = 0;
        String path = "/";
        setCookie(response, key, null, day, path);
    }

    public static Cookie getCookie(String key) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Cookie[] cookies = attr.getRequest().getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(key))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}

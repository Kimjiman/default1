package com.example.default1.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CookieUtils {
    public static void setCookie(HttpServletResponse response, String key, String value, int day, String path) {
        if(null == value) value = "";
        Cookie cookie = new Cookie(key, URLEncoder.encode(value, StandardCharsets.UTF_8));
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

    public static String getCookieValue(String key) {
        Cookie cookie = getCookie(key);
        if (cookie == null || cookie.getValue() == null) {
            return null;
        }
        return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
    }
}

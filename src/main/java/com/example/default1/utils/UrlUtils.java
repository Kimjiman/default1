package com.example.default1.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UrlUtils {
    public static String getFullDomain() {
        return getScheme() + "://" + getServerName() + ":" + getServerPort();
    }

    public static String getScheme() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getScheme();
    }

    public static String getServerName() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getServerName();
    }

    public static int getServerPort() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getServerPort();
    }
}

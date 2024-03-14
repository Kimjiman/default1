package com.example.default1.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UrlUtils {
    public static String currentDomain() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getScheme() + "://" + attr.getRequest().getServerName() +
                ":" + attr.getRequest().getServerPort();
    }
}

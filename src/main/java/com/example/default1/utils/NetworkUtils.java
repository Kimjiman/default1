package com.example.default1.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class NetworkUtils {
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static String getBrowser(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        String browser = "unknown";

        if (userAgent != null) {
            if (userAgent.contains("Edg")) { // Microsoft Edge (Chromium)
                browser = "Microsoft Edge";
            } else if (userAgent.contains("Chrome")) { // Chrome
                browser = "Google Chrome";
            } else if (userAgent.contains("Firefox")) { // Firefox
                browser = "Mozilla Firefox";
            } else if (userAgent.contains("Safari")) { // Safari
                browser = "Apple Safari";
            } else if (userAgent.contains("OPR")) { // Opera
                browser = "Opera";
            } else if (userAgent.contains("Trident/7.0")) { // Internet Explorer 11
                browser = "Internet Explorer";
            } else if (userAgent.contains("MSIE")) { // Internet Explorer 10 이하
                browser = "Internet Explorer Old";
            }
        }
        return browser.toLowerCase();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_FORWARDED");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        int index = ipAddress.indexOf('%');
        if (index != -1) {
            ipAddress = ipAddress.substring(0, index);
        }

        return ipAddress;
    }

    public static String getOs(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        String os = "unknown";

        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("windows")) {
                os = "Windows";
            } else if (userAgent.contains("macintosh") || userAgent.contains("mac os")) {
                os = "Mac OS";
            } else if (userAgent.contains("linux")) {
                os = "Linux";
            } else if (userAgent.contains("android")) {
                os = "Android";
            } else if (userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("ipod")) {
                os = "IOS";
            }
        }
        return os;
    }

    public static String getDevice(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        String device = "unknown";

        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("mobile")) {
                device = "Mobile";
            } else if (userAgent.contains("tablet")) {
                device = "Tablet";
            } else {
                device = "Desktop";
            }
        }
        return device;
    }

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

    public static String getDomain(HttpServletRequest request) {
        return request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");
    }

    public static String getReferer(HttpServletRequest request) {
        String refererHeader = request.getHeader("Referer");
        return refererHeader != null ? refererHeader.split("\\?")[0] : null;
    }

    public static boolean passReferer(HttpServletRequest request, String... refererArr) {
        if(request == null || refererArr == null) return false;
        List<String> accessUrlList = Arrays.asList(refererArr);
        String referer = getReferer(request);
        String domain = getDomain(request);
        return accessUrlList.stream()
                .anyMatch(accessUrl -> referer != null && referer.equals(domain + accessUrl));
    }
}

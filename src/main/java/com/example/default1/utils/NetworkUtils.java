package com.example.default1.utils;

import javax.servlet.http.HttpServletRequest;

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
                browser = "Internet Explorer";
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
}

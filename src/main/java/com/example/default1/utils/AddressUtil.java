package com.example.default1.utils;

import javax.servlet.http.HttpServletRequest;

public class AddressUtil {
    /**
     * UserAgent 확인
     *
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        return agent != null ? agent : "undefined";
    }

    /**
     * 브라우저 확인
     *
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        String browser = "undefine";

        if (agent != null) {
            if (agent.contains("Trident/7.0")) { // IE 11
                browser = "Trident";
            } else if (agent.contains("Edge")) { // IE-Edge
                browser = "Edge";
            } else if (agent.contains("MSIE")) { // IE 10이하
                browser = "MSIE";
            } else if (agent.contains("OPR")) { // Opera 신버젼
                browser = "OPR";
            } else if (agent.contains("Opera")) { // Opera 구버젼
                browser = "Opera";
            } else if (agent.contains("Firefox")) { // Firefox
                browser = "Firefox";
            } else if (agent.contains("Safari")) { // Chrome, Safari
                // Chrome과 Safari는 모두 Safari가 agent에 포함되어있으며, Safari의 경우 Chrome이 포함되는 경우가 존재함.
                // Safari의 경우 Version문자열이 포함되어있기 때문에, Version으로 구별한다.
                if (agent.contains("Version")) {
                    browser = "Safari";
                } else {
                    browser = "Chrome";
                }
            }
        }
        return browser.toLowerCase();
    }

    /**
     * ip확인
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        if (ip == null) {
            ip = "undefine";
        }

        return ip;
    }


    /**
     * 유저 OS 확인
     * @param request
     * @return
     */
    public static String getOs(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        String os = "undefined";
        if (agent.contains("NT 6.0")) {
            os = "Windows Vista/Server 2008";
        } else if (agent.contains("NT 5.2")) {
            os = "Windows Server 2003";
        } else if (agent.contains("NT 5.1")) {
            os = "Windows XP";
        } else if (agent.contains("NT 5.0")) {
            os = "Windows 2000";
        } else if (agent.contains("NT")) {
            os = "Windows NT";
        } else if (agent.contains("9x 4.90")) {
            os = "Windows Me";
        } else if (agent.contains("98")) {
            os = "Windows 98";
        } else if (agent.contains("95")) {
            os = "Windows 95";
        } else if (agent.contains("Win16")) {
            os = "Windows 3.x";
        } else if (agent.contains("Windows")) {
            os = "Windows";
        } else if (agent.contains("Linux")) {
            os = "Linux";
        } else if (agent.contains("Macintosh")) {
            os = "Macintosh";
        }
        return os;
    }
}

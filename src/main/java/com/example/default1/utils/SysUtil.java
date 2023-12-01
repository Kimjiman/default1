package com.example.default1.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.UUID;

public class SysUtil {
    /***
     * 파일 사이즈 <p>
     * @param file
     * @return String
     */
    public static String getFileSize(Long size) {

		/*
		String size = "0";

		if(file < 1024){
			size = file + " B";
		}else if(file >= 1024 && file < 1024 * 1024){
			size = String.format("%.2f", (double)file / 1024 ) + " KB";
		}else{
			size = String.format("%.2f", (double)file / 1024 ) + " MB";
		}

		return size;
		*/

        if (size <= 0) return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String getTime(int milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;            //초
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);   //분
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);//시

        return hours + " : " + minutes + " : " + seconds;
    }


    /**
     * Remote IP
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {

        String ip = null;
        String[] headers = {"X-FORWARDED-FOR", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

        for (String header : headers) {
            ip = request.getHeader(header);
            if (ip != null) return ip;
        }

        if (ip == null) ip = request.getRemoteAddr();

        return ip;

    }

    /**
     * short UUID
     *
     * @return
     */
    public static String getShortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        String str = Long.toString(l, Character.MAX_RADIX);
        return str;
    }


    /**
     * 세션 생성
     *
     * @param request
     * @param sessionKey
     * @param sessionValue
     */
    public static void setSession(HttpServletRequest request, String sessionKey, String sessionValue) {
        HttpSession session = request.getSession(true);
        session.setAttribute(sessionKey, sessionValue);
    }

    /**
     * 세션 호출
     *
     * @param request
     * @param sessionKey
     * @return
     */
    public static String getSession(HttpServletRequest request, String sessionKey) {
        HttpSession session = request.getSession(true);
        return (String) session.getAttribute(sessionKey);
    }


    public static String getBrowser(HttpServletRequest request) {

        String userAgent = request.getHeader("user-agent");
        String browser = "";

        if (userAgent.indexOf("Trident") > -1 || userAgent.indexOf("MSIE") > -1) { //IE

            if (userAgent.indexOf("Trident/7") > -1) {
                browser = "IE 11";
            } else if (userAgent.indexOf("Trident/6") > -1) {
                browser = "IE 10";
            } else if (userAgent.indexOf("Trident/5") > -1) {
                browser = "IE 9";
            } else if (userAgent.indexOf("Trident/4") > -1) {
                browser = "IE 8";
            } else if (userAgent.indexOf("edge") > -1) {
                browser = "IE edge";
            }

        } else if (userAgent.indexOf("Whale") > -1) { //네이버 WHALE
//			browser = "WHALE " + userAgent.split("Whale/")[1].toString().split(" ")[0].toString();
            browser = "WHALE";
        } else if (userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { //오페라
            if (userAgent.indexOf("Opera") > -1) {
//				browser = "OPERA " + userAgent.split("Opera/")[1].toString().split(" ")[0].toString();
                browser = "OPERA";
            } else if (userAgent.indexOf("OPR") > -1) {
//				browser = "OPERA " + userAgent.split("OPR/")[1].toString().split(" ")[0].toString();
                browser = "OPERA";
            }
        } else if (userAgent.indexOf("Firefox") > -1) { //파이어폭스
//			browser = "FIREFOX " + userAgent.split("Firefox/")[1].toString().split(" ")[0].toString();
            browser = "FIREFOX";
        } else if (userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1) { //사파리
//			browser = "SAFARI " + userAgent.split("Safari/")[1].toString().split(" ")[0].toString();
            browser = "SAFARI";
        } else if (userAgent.indexOf("Chrome") > -1) { //크롬
//			browser = "CHROME " + userAgent.split("Chrome/")[1].toString().split(" ")[0].toString();
            browser = "CHROME";
        }

        return browser;
    }

    /**
     * Device Type
     *
     * @param request
     * @return
     */
    public static String getDevice(HttpServletRequest request) {

        String deviceType = "";
        String userAgent = request.getHeader("User-Agent").toUpperCase();

        //안드로이드
        if (userAgent.indexOf("ANDROID") > -1) {
            deviceType = "ANDROID";
            //IOS
        } else if (userAgent.indexOf("IPHONE") > -1 || userAgent.indexOf("IPAD") > -1 || userAgent.indexOf("IPOD") > -1) {
            deviceType = "IOS";

            //아이폰, 안드로이드 외
        } else {
            deviceType = "OTHER";
        }

        return deviceType;
    }
}

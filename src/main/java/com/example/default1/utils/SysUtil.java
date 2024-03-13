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
    public static String getShortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        String str = Long.toString(l, Character.MAX_RADIX);
        return str;
    }
}

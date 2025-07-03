package com.example.default1.utils;

import com.example.default1.constants.RegexConstatns;

public class RegexUtils {
    public static boolean isId(String id) {
        return StringUtils.isRegex(id, RegexConstatns.ID);
    }

    public static boolean isPassword(String password) {
        return StringUtils.isRegex(password, RegexConstatns.PASSWORD);
    }

    public static boolean isName(String name) {
        return StringUtils.isRegex(name, RegexConstatns.NAME);
    }

    public static boolean isEmail(String email) {
        return StringUtils.isRegex(email, RegexConstatns.EMAIL);
    }

    public static boolean isMobile(String mobile) {
        return StringUtils.isRegex(mobile, RegexConstatns.MOBILE);
    }

}

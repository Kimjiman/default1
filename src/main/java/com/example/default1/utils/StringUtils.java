package com.example.default1.utils;

import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isEmpty(String val) {
        return val == null || val.isEmpty();
    }

    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }

    public static String ifEmpty(String val, String replacedVal) {
        return isEmpty(val) ? replacedVal : val;
    }

    public static boolean isBlank(String val) {
        return val == null || val.trim().isEmpty();
    }

    public static boolean isNotBlank(String val) {
        return !isBlank(val);
    }

    public static String ifBlank(String val, String replacedVal) {
        return isBlank(val) ? replacedVal : val;
    }

    public static String removeWhitespace(String val) {
        if (isEmpty(val)) return val;
        return val.replaceAll("\\s", "");
    }

    public static String removeSpecialCharacters(String val) {
        if (isEmpty(val)) return val;

        String blackList = "!@#$%^&*()_+{}[]|;:'\",.<>?/";
        for (char blackChar : blackList.toCharArray()) {
            val = val.replace(String.valueOf(blackChar), "");
        }

        return val;
    }

    public static String removeChar(String val, char charVal) {
        if (isEmpty(val)) return val;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) == charVal) {
                continue;
            }
            sb.append(val.charAt(i));
        }
        return sb.toString();
    }

    public static <T extends Collection<String>> String joinStrings(T val, String delimiter) {
        if (val.isEmpty()) return "";
        return String.join(delimiter, val);
    }

    public static String capitalizeFirstLetter(String val) {
        if (isEmpty(val)) return val;
        return Character.toUpperCase(val.charAt(0)) + val.substring(1);
    }

    public static String matchingRegexFromString(String val, String regex) {
        if (isEmpty(val)) return val;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(val);

        if (matcher.find()) {
            return matcher.group();
        }

        return "";
    }

    public static boolean isStringMatchingRegex(String val, String regex) {
        if (isEmpty(val)) return false;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(val);

        return matcher.matches();
    }

    public static String maksingFromString(String val, int count, Character maskingChar) {
        if (isEmpty(val) || count <= 0) return val;

        int length = Math.min(count, val.length());
        StringBuilder masked = new StringBuilder(val);

        for (int i = 0; i < length; i++) {
            masked.setCharAt(i, maskingChar);
        }

        return masked.toString();
    }
}

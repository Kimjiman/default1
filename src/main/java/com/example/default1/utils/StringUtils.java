package com.example.default1.utils;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullOrNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static String removeWhitespace(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.replaceAll("\\s", "");
    }

    public static String removeSpecialCharacters(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }

        String blackList = "!@#$%^&*()_+{}[]|;:'\",.<>?/";
        for (char blackChar : blackList.toCharArray()) {
            str = str.replace(String.valueOf(blackChar), "");
        }

        return str;
    }

    public static String removeChar(String str, char cha) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == cha) {
                continue;
            }
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static <T extends Collection<String>> String joinStrings(T strings, String delimiter) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }
        return String.join(delimiter, strings);
    }


    public static String capitalizeFirstLetter(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String matchingRegexFromString(String str, String regex) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return matcher.group();
        }

        return "";
    }

    public static boolean isStringMatchingRegex(String str, String regex) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static String maksingFromString(String str, int count, Character maskingChar) {
        if (isNullOrEmpty(str) || count <= 0) {
            return str;
        }
        int length = Math.min(count, str.length());
        StringBuilder masked = new StringBuilder(str);

        for (int i = 0; i < length; i++) {
            masked.setCharAt(i, maskingChar);
        }

        return masked.toString();
    }
}

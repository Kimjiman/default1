package com.example.default1.utils;

import java.util.Collection;
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
        return isEmpty(val) ? val : replacedVal;
    }

    public static boolean isBlank(String val) {
        return val == null || val.trim().isEmpty();
    }

    public static boolean isNotBlank(String val) {
        return !isBlank(val);
    }

    public static String ifBlank(String val, String replacedVal) {
        return isBlank(val) ? val : replacedVal;
    }

    public static String removeWhiteSpace(String val) {
        return isEmpty(val) ? val : val.replaceAll("\\s", "");
    }

    /**
     * 문자(\p{L}), 숫자(p{N}), 빈칸(\s)을 제외한 모든 특수문자를 제거한다.
     * @param val 입력 문자열
     * @return 문자, 숫자, 빈칸
     */
    public static String removeSpecialCharacters(String val) {
        return isEmpty(val) ? val : val.replaceAll("[^\\p{L}\\p{N}\\s]", "");
    }

    public static String remove(String val, Character charVal) {
        if (isEmpty(val) || charVal == null) return val;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) != charVal) {
                sb.append(val.charAt(i));
            }
        }
        return sb.toString();
    }

    public static String upperCaseFirstCharacter(String val) {
        return isEmpty(val) ? val : Character.toUpperCase(val.charAt(0)) + val.substring(1);
    }

    public static String matchingRegex(String val, String regex) {
        if (isEmpty(val) || isEmpty(regex)) return val;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(val);

        return matcher.find() ?  matcher.group() : val;
    }

    public static boolean isRegex(String val, String regex) {
        if (isEmpty(val) || isEmpty(regex)) return false;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(val);

        return matcher.matches();
    }

    public static boolean isNumber(String val) {
        if (isEmpty(val)) return false;
        return !isEmpty(val) && isRegex(val, "\\d+");
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return (str1 == null && str2 == null) || (str1 != null && str1.equalsIgnoreCase(str2));
    }

    public static int indexOf(String val, char target) {
        return isEmpty(val) ? -1 : val.indexOf(target);
    }

    public static String reverse(String val) {
        return isEmpty(val) ? val : new StringBuilder(val).reverse().toString();
    }

    public static String masking(String val, int start, int length, Character maskingCharacter) {
        if (isEmpty(val) || length <= 0) return val;

        StringBuilder masked = new StringBuilder(val);
        int maxLength = Math.min(length, val.length());
        for (int i = start; i < maxLength; i++) {
            masked.setCharAt(i, maskingCharacter);
        }

        return masked.toString();
    }


    public static String joinStrings(Collection<String> val, String delimiter) {
        return val == null ? "" : String.join(delimiter, val);
    }
}

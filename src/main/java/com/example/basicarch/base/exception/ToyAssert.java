package com.example.default1.base.exception;

import com.example.default1.base.utils.StringUtils;

import java.util.Collection;

public class ToyAssert {

    /**
     * value가 blank이면 예외 발생
     */
    public static void notBlank(String value, ErrorCode errorCode) {
        if (StringUtils.isBlank(value)) {
            throw new CustomException(errorCode);
        }
    }

    public static void notBlank(String value, ErrorCode errorCode, String message) {
        if (StringUtils.isBlank(value)) {
            throw new CustomException(errorCode, message);
        }
    }

    /**
     * value가 null이면 예외 발생
     */
    public static void notNull(Object value, ErrorCode errorCode) {
        if (value == null) {
            throw new CustomException(errorCode);
        }
    }

    public static void notNull(Object value, ErrorCode errorCode, String message) {
        if (value == null) {
            throw new CustomException(errorCode, message);
        }
    }

    /**
     * condition이 true이면 예외 발생
     */
    public static void isTrue(boolean condition, ErrorCode errorCode) {
        if (condition) {
            throw new CustomException(errorCode);
        }
    }

    public static void isTrue(boolean condition, ErrorCode errorCode, String message) {
        if (condition) {
            throw new CustomException(errorCode, message);
        }
    }

    /**
     * collection이 null이거나 비어있으면 예외 발생
     */
    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        if (collection == null || collection.isEmpty()) {
            throw new CustomException(errorCode);
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCode errorCode, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new CustomException(errorCode, message);
        }
    }
}

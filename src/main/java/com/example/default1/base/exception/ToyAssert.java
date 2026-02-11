package com.example.default1.base.exception;

import org.springframework.util.Assert;

public class ToyAssert extends Assert {
    public static void throwEx(Integer status, String message) throws Exception {
        throw new CustomException(status, message);
    }
}

package com.example.default1.utils;

import java.util.UUID;

public class RandomUtils {
    public static String generateRandomString(int length) {
        if(length < 6) length = 6;
        if(length > 32) length = 32;
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, length).toUpperCase();
    }
}

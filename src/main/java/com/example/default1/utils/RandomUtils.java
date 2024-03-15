package com.example.default1.utils;

import java.util.function.Supplier;

public class RandomUtils {
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateRandomString(int length) {
        if (length < 0) length = 1;

        Supplier<Character> randomCharSupplier = () -> ALLOWED_CHARACTERS.charAt((int) (Math.random() * ALLOWED_CHARACTERS.length()));

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(randomCharSupplier.get());
        }

        return sb.toString();
    }
}

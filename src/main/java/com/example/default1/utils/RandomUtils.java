package com.example.default1.utils;

import java.security.SecureRandom;
import java.util.function.Supplier;

public class RandomUtils {
    private static final String ALLOWED_CHARACTERS;
    private static final int DEFAULT_LENGTH = 8;

    static {
        StringBuilder sb = new StringBuilder();
        // a ~ z
        for (char c = 97; c <= 122; c++) {
            sb.append(c);
        }
        // A ~ Z
        for (char c = 65; c <= 90; c++) {
            sb.append(c);
        }
        // 0 - 9
        for (char c = 48; c <= 57; c++) {
            sb.append(c);
        }
        ALLOWED_CHARACTERS = sb.toString();
    }

    public static String generateRandomString(int length) {
        if (length < 0) length = DEFAULT_LENGTH;

        Supplier<Character> randomCharSupplier = () -> {
            SecureRandom secureRandom = new SecureRandom();
            return ALLOWED_CHARACTERS.charAt(secureRandom.nextInt(ALLOWED_CHARACTERS.length()));
        };

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(randomCharSupplier.get());
        }

        return sb.toString();
    }

    public static String generateRandomString() {
        return generateRandomString(DEFAULT_LENGTH);
    }
}

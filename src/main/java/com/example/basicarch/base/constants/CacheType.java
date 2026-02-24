package com.example.default1.base.constants;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CacheType {
    CODE("code"),
    MENU("menu");

    private final String value;

    CacheType(String value) {
        this.value = value;
    }

    public static CacheType fromValue(String value) {
        return Arrays.stream(CacheType.values())
                .filter(it -> it.value.equals(value))
                .findAny()
                .orElse(null);
    }
}

package com.example.default1.constants;

import com.example.default1.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum YesNo {
    YES ("Y", true),
    NO  ("N", false);

    private final String key;
    private final boolean value;

    YesNo(String key, boolean value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    @JsonValue
    public boolean isValue() {
        return value;
    }

    @JsonCreator
    public static YesNo of(String key) {
        if(StringUtils.isEmpty(key)) {
            throw new CustomException(2700, "key is null");
        }

        StringBuilder sb = new StringBuilder();

        for(YesNo yn : YesNo.values()) {
            if(yn.key.equals(key)) {
                return yn;
            }
        }

        for(YesNo yn : YesNo.values()) {
            sb.append(yn.getKey()).append(",");
        }

        throw new CustomException(2700, "key is [" + sb.substring(0, sb.length() - 1) + "]");
    }
}

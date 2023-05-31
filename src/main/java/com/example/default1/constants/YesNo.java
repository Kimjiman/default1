package com.example.default1.constants;

import com.example.default1.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum YesNo {
    YES (true, "Y"),
    NO  (false, "N");

    private final boolean key;
    private final String value;

    YesNo(boolean key, String value) {
        this.key = key;
        this.value = value;
    }

    public boolean getKey() {
        return key;
    }

    @JsonValue
    public String isValue() {
        return value;
    }

    @JsonCreator
    public static YesNo ofKey(Boolean key) {
        if(key == null) {
            throw new CustomException(2700, "key is null");
        }

        StringBuilder sb = new StringBuilder();

        for(YesNo yn : YesNo.values()) {
            if(yn.key == key) {
                return yn;
            }
        }

        for(YesNo yn : YesNo.values()) {
            sb.append(yn.getKey()).append(",");
        }

        throw new CustomException(2700, "key is [" + sb.substring(0, sb.length() - 1) + "]");
    }
}

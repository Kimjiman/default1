package com.example.default1.constants.enumConverter;

import com.example.default1.constants.enumModel.YN;
import org.springframework.core.convert.converter.Converter;

// Get, Delete와 같이 parameter로 넘어오는 value를 enum으로 일치시킴
public class YnToEnumConverter implements Converter<String, YN> {
    @Override
    public YN convert(String key) {
        return YN.of(key);
    }
}

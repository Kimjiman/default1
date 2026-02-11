package com.example.default1.base.converter;

import com.example.default1.base.constants.YN;
import org.springframework.core.convert.converter.Converter;

public class YnToEnumConverter implements Converter<String, YN> {
    @Override
    public YN convert(String key) {
        return YN.of(key);
    }
}

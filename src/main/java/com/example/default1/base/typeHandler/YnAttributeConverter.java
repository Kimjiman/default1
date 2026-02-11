package com.example.default1.base.typeHandler;

import com.example.default1.base.constants.YN;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class YnAttributeConverter implements AttributeConverter<YN, String> {
    @Override
    public String convertToDatabaseColumn(YN yn) {
        return yn != null ? yn.getValue() : null;
    }

    @Override
    public YN convertToEntityAttribute(String value) {
        if (value == null) return null;
        return YN.fromValue(value);
    }
}

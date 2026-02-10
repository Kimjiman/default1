package com.example.default1.base.model;

import com.example.default1.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseObject {
    public boolean isEmpty() {
        Class<?> currentClass = this.getClass();

        try {
            while (currentClass != null && currentClass != Object.class) {
                Field[] fields = currentClass.getDeclaredFields();

                for (Field field : fields) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    field.setAccessible(true);
                    Object value = field.get(this);
                    if (value != null) {
                        return false;
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }
}

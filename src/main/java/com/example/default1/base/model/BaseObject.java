package com.example.default1.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Field;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseObject {
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName()).append(":{ \n");

        Class<?> currentClass = this.getClass();

        try {
            while (currentClass != null) {
                Field[] fields = currentClass.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    if (fields.length == 0) {
                        currentClass = currentClass.getSuperclass();
                        continue;
                    }

                    String value = null;
                    if (field.get(this) != null) {
                        value = field.get(this).toString();
                    }

                    sb.append("    ").append(field.getName()).append(": ").append(value).append(", \n");

                }

                if (currentClass.getSuperclass() != null) {
                    currentClass = currentClass.getSuperclass();
                }
            }
        } catch (IllegalAccessException e) {
            sb.append("Error accessing fileds");
        }

        sb.setLength(sb.length() - 3);
        sb.append("\n}");
        return sb.toString();
    }


    public boolean isEmpty() {
        Class<?> currentClass = this.getClass();

        boolean flag = true;

        try {
            mainLoop:
            while (currentClass != null) {
                Field[] fields = currentClass.getDeclaredFields();
                if (fields.length == 0) {
                    currentClass = currentClass.getSuperclass();
                }

                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(this);
                    if (value != null) {
                        flag = false;
                        break mainLoop;
                    }

                    if (currentClass != null) {
                        currentClass = currentClass.getSuperclass();
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return flag;
    }
}

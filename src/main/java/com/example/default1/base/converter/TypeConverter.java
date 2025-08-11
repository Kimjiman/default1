package com.example.default1.base.converter;

import com.example.default1.utils.DateUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * packageName    : com.example.default1.base.converter
 * fileName       : NamedConverter
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@Component
public interface TypeConverter {
    @Named("stringToLocalDateTime")
    default LocalDateTime stringToLocalDateTime(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }
        return DateUtils.stringToLocalDateTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return DateUtils.localDateTimeToString(time, "yyyy-MM-dd HH:mm:ss");
    }
}

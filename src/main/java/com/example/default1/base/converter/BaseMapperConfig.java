package com.example.default1.base.converter;

import com.example.default1.base.dto.BaseDTO;
import com.example.default1.base.model.BaseModel;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * packageName    : com.example.default1.base.converter
 * fileName       : MapperConfig
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@MapperConfig(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface BaseMapperConfig {
}

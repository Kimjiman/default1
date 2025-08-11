package com.example.default1.module.code.converter;

import com.example.default1.base.converter.BaseMapperConfig;
import com.example.default1.base.converter.TypeConverter;
import com.example.default1.module.code.dto.CodeGroupDTO;
import com.example.default1.module.code.model.CodeGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * packageName    : com.example.default1.module.code.converter
 * fileName       : CodeMapper
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@Mapper(
        componentModel = "spring",
        config = BaseMapperConfig.class,
        uses = TypeConverter.class
)
public interface CodeGroupConverter {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createId", target = "createId")
    @Mapping(source = "createTime", target = "createTime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "updateId", target = "updateId")
    @Mapping(source = "updateTime", target = "updateTime", qualifiedByName = "stringToLocalDateTime")
    CodeGroupDTO toDto(CodeGroup codeGroup);

    @Mapping(target = "rowNum", ignore = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createId", target = "createId")
    @Mapping(source = "createTime", target = "createTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "updateId", target = "updateId")
    @Mapping(source = "updateTime", target = "updateTime", qualifiedByName = "localDateTimeToString")
    CodeGroup fromDto(CodeGroupDTO codeGroupDTO);
}

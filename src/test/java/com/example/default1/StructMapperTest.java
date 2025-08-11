package com.example.default1;

import com.example.default1.module.code.converter.CodeConverter;
import com.example.default1.module.code.dto.CodeDTO;
import com.example.default1.module.code.model.Code;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * packageName    : com.example.default1
 * fileName       : StructMapperTest
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
public class StructMapperTest {
    private CodeConverter codeConverter;

    private static final Logger log = LoggerFactory.getLogger(StructMapperTest.class);

    @BeforeEach
    public void setUp() {
        this.codeConverter = Mappers.getMapper(CodeConverter.class);
    }

    @Test
    void test() {

        Code code = Code.builder()
                .id(1L)
                .name("codeName")
                .createId(1L)
                .createTime("2012-12-11 03:11:11")
                .updateId(1L)
                .updateTime("2012-12-11 03:11:11")
                .build();

        log.info("source code object={}", code.toJson());

        CodeDTO codeDTO = codeConverter.toDto(code);

        log.info("codeDTO={}", codeDTO.toJson());
    }
}

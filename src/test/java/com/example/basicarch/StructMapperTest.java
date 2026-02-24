package com.example.basicarch;

import com.example.basicarch.module.code.converter.CodeConverter;
import com.example.basicarch.module.code.model.CodeModel;
import com.example.basicarch.module.code.entity.Code;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class StructMapperTest {
    @Autowired
    private CodeConverter codeConverter;

    private static final Logger log = LoggerFactory.getLogger(StructMapperTest.class);

    @Test
    void test() {
        Code code = Code.builder()
                .id(1L)
                .name("codeName")
                .createId(1L)
                .createTime(LocalDateTime.of(2012, 12, 11, 3, 11, 11))
                .updateId(1L)
                .updateTime(LocalDateTime.of(2012, 12, 11, 3, 11, 11))
                .build();

        log.info("source code object={}", code.toJson());

        CodeModel codeModel = codeConverter.toModel(code);

        log.info("codeModel={}", codeModel.toJson());
    }
}

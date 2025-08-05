package com.example.default1;

import com.example.default1.module.code.Code;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * packageName    : com.example.default1
 * fileName       : ModelTest
 * author         : KIM JIMAN
 * date           : 25. 7. 4. 금요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 7. 4.     KIM JIMAN      First Commit
 */
@SpringBootTest
public class ModelTest {
    private static final Logger log = LoggerFactory.getLogger(ModelTest.class);

    @Test
    public void toStringTest() {
        Code code = Code.builder()
                .code("code")
                .name("name")
                .codeGroupId(1L)
                .id(1L)
                .codeGroupName("codeGroupName")
                .build();

        log.info("code={}", code);
    }
}

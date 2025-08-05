package com.example.default1;

import com.example.default1.base.model.pager.PageInfo;
import com.example.default1.base.model.pager.PageResponse;
import com.example.default1.module.user.model.User;
import groovy.util.logging.Slf4j;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/**
 * packageName    : com.example.default1
 * fileName       : PagerTests
 * author         : KIM JIMAN
 * date           : 25. 8. 5. 화요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 5.     KIM JIMAN      First Commit
 */
@Slf4j
public class PagerTests {
    private static final Logger log = LoggerFactory.getLogger(PagerTests.class);

    @Test
    void test() {
        long totalRow = 29;
        int limit = 10;
        int pageSize = 3;

        List<User> userList = Instancio.ofList(User.class)
                .size(9)
                .create();

        PageInfo pageInfo = PageInfo.of(3, totalRow, limit, pageSize);
        PageResponse<User> pageResponse = new PageResponse<>(pageInfo, userList);

        pageResponse.getList().forEach(user -> log.info("rowNum: {}", user.getRowNum()));
    }
}

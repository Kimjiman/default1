package com.example.default1.module.user.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * packageName    : com.example.default1.module.user.facade
 * fileName       : UserFacade
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@Facade
@RequiredArgsConstructor
@Slf4j
public class UserFacade {
    private final UserService userService;
}

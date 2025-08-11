package com.example.default1.module.menu.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * packageName    : com.example.default1.module.menu.facade
 * fileName       : MenuFacade
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@Facade
@Slf4j
@RequiredArgsConstructor
public class MenuFacade {
    private final MenuService menuService;
}

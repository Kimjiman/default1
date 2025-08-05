package com.example.default1.module.menu.controller;

import com.example.default1.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.default1.base.menu
 * fileName       : MenuController
 * author         : KIM JIMAN
 * date           : 24. 7. 11. 목요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 24. 7. 11.     KIM JIMAN      First Commit
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MenuController {
    private final MenuService menuService;
}

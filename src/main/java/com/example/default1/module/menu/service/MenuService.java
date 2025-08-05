package com.example.default1.module.menu.service;

import com.example.default1.module.menu.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.example.default1.base.menu
 * fileName       : MenuService
 * author         : KIM JIMAN
 * date           : 24. 7. 11. 목요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 24. 7. 11.     KIM JIMAN      First Commit
 */
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuMapper menuMapper;

}

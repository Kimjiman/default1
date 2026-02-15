package com.example.default1.module.menu.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.base.exception.CustomException;
import com.example.default1.base.exception.SystemErrorCode;
import com.example.default1.base.exception.ToyAssert;
import com.example.default1.module.menu.converter.MenuConverter;
import com.example.default1.module.menu.entity.Menu;
import com.example.default1.module.menu.model.MenuModel;
import com.example.default1.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Facade
@Slf4j
@RequiredArgsConstructor
public class MenuFacade {
    private final MenuService menuService;
    private final MenuConverter menuConverter;

    public List<MenuModel> findAll() {
        return menuConverter.toModelList(menuService.findAllCached());
    }

    public List<MenuModel> findByUseYn(String useYn) {
        return menuConverter.toModelList(menuService.findByUseYnCached(useYn));
    }

    public MenuModel findById(Long id) {
        ToyAssert.notNull(id, SystemErrorCode.REQUIRED, "ID를 입력해주세요.");
        Menu menu = menuService.findById(id).orElseThrow(() ->
                new CustomException(SystemErrorCode.NOT_FOUND, "메뉴를 찾을 수 없습니다."));
        return menuConverter.toModel(menu);
    }

    public MenuModel create(MenuModel menuModel) {
        Menu menu = menuConverter.toEntity(menuModel);
        Menu saved = menuService.save(menu);
        menuService.refreshCache();
        return menuConverter.toModel(saved);
    }

    public MenuModel update(MenuModel menuModel) {
        ToyAssert.notNull(menuModel.getId(), SystemErrorCode.REQUIRED, "ID를 입력해주세요.");
        Menu menu = menuConverter.toEntity(menuModel);
        Menu saved = menuService.save(menu);
        menuService.refreshCache();
        return menuConverter.toModel(saved);
    }

    public void removeById(Long id) {
        ToyAssert.notNull(id, SystemErrorCode.REQUIRED, "ID를 입력해주세요.");
        menuService.deleteById(id);
        menuService.refreshCache();
    }
}

package com.example.default1.module.menu.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.base.constants.YN;
import com.example.default1.base.exception.CustomException;
import com.example.default1.base.exception.SystemErrorCode;
import com.example.default1.base.exception.ToyAssert;
import com.example.default1.module.menu.converter.MenuConverter;
import com.example.default1.module.menu.entity.Menu;
import com.example.default1.module.menu.model.MenuModel;
import com.example.default1.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Facade
@Slf4j
@RequiredArgsConstructor
public class MenuFacade {
    private final MenuService menuService;
    private final MenuConverter menuConverter;

    public List<MenuModel> findAll() {
        return menuConverter.toModelList(menuService.findAllCached());
    }

    public List<MenuModel> findAllTree() {
        List<MenuModel> flatList = menuConverter.toModelList(menuService.findAllCached());
        return buildTree(flatList);
    }

    public List<MenuModel> findByUseYn(YN useYn) {
        return menuConverter.toModelList(menuService.findByUseYnCached(useYn.getValue()));
    }

    public List<MenuModel> findTreeByUseYn(YN useYn) {
        List<MenuModel> flatList = menuConverter.toModelList(menuService.findByUseYnCached(useYn.getValue()));
        return buildTree(flatList);
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

    @Transactional
    public void removeById(Long id) {
        ToyAssert.notNull(id, SystemErrorCode.REQUIRED, "ID를 입력해주세요.");
        deleteRecursive(id);
        menuService.refreshCache();
    }

    private void deleteRecursive(Long parentId) {
        List<Menu> children = menuService.findByParentId(parentId);
        for (Menu child : children) {
            deleteRecursive(child.getId());
        }
        menuService.deleteById(parentId);
    }

    private List<MenuModel> buildTree(List<MenuModel> flatList) {
        Map<Long, MenuModel> menuMap = flatList.stream()
                .peek(m -> m.setChildren(new ArrayList<>()))
                .collect(Collectors.toMap(MenuModel::getId, Function.identity()));

        List<MenuModel> roots = new ArrayList<>();
        for (MenuModel menu : menuMap.values()) {
            if (menu.getParentId() == null) {
                roots.add(menu);
            } else {
                MenuModel parent = menuMap.get(menu.getParentId());
                if (parent != null) {
                    parent.getChildren().add(menu);
                }
            }
        }

        Comparator<MenuModel> byOrder = Comparator.comparing(
                MenuModel::getOrder, Comparator.nullsLast(Comparator.naturalOrder()));
        sortRecursive(roots, byOrder);
        return roots;
    }

    private void sortRecursive(List<MenuModel> menus, Comparator<MenuModel> comparator) {
        menus.sort(comparator);
        for (MenuModel menu : menus) {
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                sortRecursive(menu.getChildren(), comparator);
            }
        }
    }
}

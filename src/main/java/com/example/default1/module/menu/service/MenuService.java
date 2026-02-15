package com.example.default1.module.menu.service;

import com.example.default1.base.service.BaseService;
import com.example.default1.module.menu.entity.Menu;
import com.example.default1.module.menu.model.MenuSearchParam;
import com.example.default1.module.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService implements BaseService<Menu, MenuSearchParam, Long> {
    private final MenuRepository menuRepository;
    private final ConcurrentHashMap<String, List<String>> roleCache = new ConcurrentHashMap<>();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @PostConstruct
    public void init() {
        refreshCache();
    }

    public void refreshCache() {
        List<Menu> menus = menuRepository.findByUseYn("Y");
        ConcurrentHashMap<String, List<String>> newCache = new ConcurrentHashMap<>();

        for (Menu menu : menus) {
            if (menu.getUri() != null && menu.getRoleList() != null && !menu.getRoleList().isEmpty()) {
                newCache.put(menu.getUri(), menu.getRoleList());
            }
        }

        roleCache.clear();
        roleCache.putAll(newCache);
        log.info("Menu role cache refreshed. entries={}", roleCache.size());
    }

    public List<String> findRolesByUri(String uri) {
        // 정확 매칭 우선
        List<String> roles = roleCache.get(uri);
        if (roles != null) {
            return roles;
        }

        // AntPathMatcher 패턴 매칭
        for (Map.Entry<String, List<String>> entry : roleCache.entrySet()) {
            if (pathMatcher.match(entry.getKey(), uri)) {
                return entry.getValue();
            }
        }

        // 등록되지 않은 URI → null (허용)
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return menuRepository.existsById(id);
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public List<Menu> findAllBy(MenuSearchParam param) {
        return menuRepository.findAll();
    }

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) return;
        menuRepository.deleteById(id);
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public List<Menu> findByUseYn(String useYn) {
        return menuRepository.findByUseYn(useYn);
    }
}

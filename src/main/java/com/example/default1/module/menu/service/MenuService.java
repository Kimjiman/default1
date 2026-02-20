package com.example.default1.module.menu.service;

import com.example.default1.base.constants.CacheType;
import com.example.default1.base.redis.CacheEventHandler;
import com.example.default1.base.service.BaseService;
import com.example.default1.base.utils.JsonUtils;
import com.example.default1.module.menu.entity.Menu;
import com.example.default1.module.menu.model.MenuSearchParam;
import com.example.default1.module.menu.repository.MenuRepository;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService implements BaseService<Menu, MenuSearchParam, Long>, CacheEventHandler {
    private static final String ROLE_CACHE_KEY = "cache:menu:role";
    private static final String MENU_CACHE_KEY = "cache:menu";
    private static final String MENU_FIELD_ALL = "all";
    private static final String MENU_FIELD_USE_Y = "useYn:Y";

    private final MenuRepository menuRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public CacheType getSupportedCacheType() {
        return CacheType.MENU;
    }

    @Override
    public void handle() {
        refreshCache();
    }

    @PostConstruct
    public void init() {
        try {
            refreshCache();
        } catch (Exception e) {
            log.warn("[MenuService] 초기 캐시 로드 실패 (Redis 미준비?): {}", e.getMessage());
        }
    }

    public void refreshCache() {
        List<Menu> allMenus = menuRepository.findAll();
        List<Menu> activeMenus = menuRepository.findByUseYn("Y");

        refreshRoleCache(activeMenus);
        refreshMenuCache(allMenus, activeMenus);
    }

    private void refreshRoleCache(List<Menu> activeMenus) {
        Map<String, String> newCache = new HashMap<>();

        for (Menu menu : activeMenus) {
            if (menu.getUri() != null && menu.getRoleList() != null && !menu.getRoleList().isEmpty()) {
                newCache.put(menu.getUri(), String.join(",", menu.getRoleList()));
            }
        }

        stringRedisTemplate.delete(ROLE_CACHE_KEY);
        if (!newCache.isEmpty()) {
            stringRedisTemplate.opsForHash().putAll(ROLE_CACHE_KEY, newCache);
        }
        log.info("Menu role cache refreshed. entries={}", newCache.size());
    }

    private void refreshMenuCache(List<Menu> allMenus, List<Menu> activeMenus) {
        Map<String, String> newCache = new HashMap<>();
        newCache.put(MENU_FIELD_ALL, JsonUtils.toJson(allMenus));
        newCache.put(MENU_FIELD_USE_Y, JsonUtils.toJson(activeMenus));

        stringRedisTemplate.delete(MENU_CACHE_KEY);
        stringRedisTemplate.opsForHash().putAll(MENU_CACHE_KEY, newCache);
        log.info("Menu cache refreshed. all={}, active={}", allMenus.size(), activeMenus.size());
    }

    public List<Menu> findAllCached() {
        Object value = stringRedisTemplate.opsForHash().get(MENU_CACHE_KEY, MENU_FIELD_ALL);
        if (value != null) {
            return JsonUtils.fromJson(value.toString(), new TypeToken<List<Menu>>() {}.getType());
        }
        return menuRepository.findAll();
    }

    public List<Menu> findByUseYnCached(String useYn) {
        if ("Y".equals(useYn)) {
            Object value = stringRedisTemplate.opsForHash().get(MENU_CACHE_KEY, MENU_FIELD_USE_Y);
            if (value != null) {
                return JsonUtils.fromJson(value.toString(), new TypeToken<List<Menu>>() {}.getType());
            }
        }
        return menuRepository.findByUseYn(useYn);
    }

    public List<String> findRolesByUri(String uri) {
        Object value = stringRedisTemplate.opsForHash().get(ROLE_CACHE_KEY, uri);
        if (value != null) {
            return Arrays.asList(value.toString().split(","));
        }

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(ROLE_CACHE_KEY);
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            if (pathMatcher.match(entry.getKey().toString(), uri)) {
                return Arrays.asList(entry.getValue().toString().split(","));
            }
        }

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

    public List<Menu> findByParentId(Long parentId) {
        return menuRepository.findByParentId(parentId);
    }

    public void deleteByParentId(Long parentId) {
        menuRepository.deleteByParentId(parentId);
    }
}

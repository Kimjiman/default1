package com.example.default1.config.scheduler;

import com.example.default1.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CacheScheduler {
    private final MenuService menuService;

    @Scheduled(cron = "${cron.cache.refresh-cache}")
    public void refreshCache() {
        menuService.refreshCache();
    }
}

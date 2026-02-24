package com.example.basicarch.config.scheduler;

import com.example.basicarch.module.code.service.CodeService;
import com.example.basicarch.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CacheScheduler {
    private final MenuService menuService;
    private final CodeService codeService;

    @Scheduled(cron = "${cron.cache.refresh-menu}")
    public void refreshMenuCache() {
        menuService.refreshCache();
    }

    @Scheduled(cron = "${cron.cache.refresh-code}")
    public void refreshCodeCache() {
        codeService.refreshCache();
    }
}

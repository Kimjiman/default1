package com.example.default1.base.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheScheduler {
    @Scheduled(cron = "${cron.cache.refresh-cache}")
    public void refreshCache() {

    }
}

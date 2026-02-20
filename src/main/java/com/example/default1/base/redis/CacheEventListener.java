package com.example.default1.base.redis;

import com.example.default1.base.constants.CacheType;
import com.example.default1.module.code.service.CodeService;
import com.example.default1.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheEventListener implements MessageListener {
    private final CodeService codeService;
    private final MenuService menuService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String value = new String(message.getBody(), StandardCharsets.UTF_8);
        CacheType cacheType = CacheType.fromValue(value);

        if (cacheType == null) {
            log.warn("[CacheEvent] 알 수 없는 캐시 타입: {}", value);
            return;
        }

        switch (cacheType) {
            case CODE:
                codeService.refreshCache();
                break;
            case MENU:
                menuService.refreshCache();
                break;
        }
    }
}

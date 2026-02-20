package com.example.default1.base.redis;

import com.example.default1.base.constants.CacheType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CacheEventListener implements MessageListener {

    private final Map<CacheType, CacheEventHandler> handlerMap;

    public CacheEventListener(List<CacheEventHandler> handlers) {
        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(CacheEventHandler::getSupportedCacheType, h -> h));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String value = new String(message.getBody(), StandardCharsets.UTF_8);
        CacheType cacheType = CacheType.fromValue(value);

        if (cacheType == null) {
            log.warn("[CacheEvent] 알 수 없는 캐시 타입: {}", value);
            return;
        }

        CacheEventHandler handler = handlerMap.get(cacheType);
        if (handler == null) {
            log.warn("[CacheEvent] 등록된 핸들러 없음: {}", cacheType);
            return;
        }

        try {
            handler.handle();
            log.info("[CacheEvent] 캐시 갱신 완료: {}", cacheType);
        } catch (Exception e) {
            log.error("[CacheEvent] 캐시 갱신 실패: {}", cacheType, e);
        }
    }
}

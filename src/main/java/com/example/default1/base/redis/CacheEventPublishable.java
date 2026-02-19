package com.example.default1.base.redis;

import com.example.default1.base.constants.CacheType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

public interface CacheEventPublishable {

    String CHANNEL = "cache:invalidate";

    Publisher getCacheEventPublisher();

    CacheType getCacheType();

    default void publishCacheEvent() {
        getCacheEventPublisher().publish(getCacheType());
    }

    @Component
    @RequiredArgsConstructor
    class Publisher {
        private final StringRedisTemplate redisTemplate;

        public void publish(CacheType cacheType) {
            redisTemplate.convertAndSend(CHANNEL, cacheType.getValue());
        }
    }
}

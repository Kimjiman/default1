package com.example.default1.base.redis;

import com.example.default1.base.constants.CacheType;

public interface CacheEventHandler {

    CacheType getSupportedCacheType();

    void handle();
}

package com.example.basicarch.base.redis;

import com.example.basicarch.base.constants.CacheType;

public interface CacheEventHandler {

    CacheType getSupportedCacheType();

    void handle();
}

package com.example.default1.base.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 3)
@ToString
@Getter
@Setter
public class RedisObject {
    @Id
    private String key;
    private String value;

    public RedisObject(final String key, final String value) {
        this.key = key;
        this.value = value;
    }
}

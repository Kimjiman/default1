package com.example.default1.base.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private static final Long EXPIRE_TIME = 3L;

    public RedisRepository(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Key, Value Redis에 저장
     * @param redisObject
     */
    public void save(final RedisObject redisObject) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(redisObject.getKey(), redisObject.getValue());
        redisTemplate.expire(redisObject.getKey(), EXPIRE_TIME, TimeUnit.DAYS);
    }

    /**
     * Key로 Value 찾기
     * @param key key
     * @return value
     */
    public Optional<RedisObject> findValueByKey(final String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(key);

        if (Objects.isNull(value)) {
            return Optional.empty();
        }
        return Optional.of(new RedisObject(value, key));
    }

    /**
     * Key로 한행삭제
     * @param key 
     */
    public Boolean deleteRawByKey(String key) {
        return redisTemplate.delete(key);
    }
}

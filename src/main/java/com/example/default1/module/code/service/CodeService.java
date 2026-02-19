package com.example.default1.module.code.service;

import com.example.default1.base.service.BaseService;
import com.example.default1.base.utils.JsonUtils;
import com.example.default1.module.code.entity.Code;
import com.example.default1.module.code.model.CodeSearchParam;
import com.example.default1.module.code.repository.CodeRepository;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeService implements BaseService<Code, CodeSearchParam, Long> {
    private static final String CACHE_KEY = "cache:code";
    private static final String CACHE_FIELD_ALL = "all";

    private final CodeRepository codeRepository;
    private final StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init() {
        refreshCache();
    }

    public void refreshCache() {
        List<Code> codes = codeRepository.findAllBy(new CodeSearchParam());

        Map<String, String> newCache = new HashMap<>();
        newCache.put(CACHE_FIELD_ALL, JsonUtils.toJson(codes));

        stringRedisTemplate.delete(CACHE_KEY);
        stringRedisTemplate.opsForHash().putAll(CACHE_KEY, newCache);
        log.info("Code cache refreshed. entries={}", codes.size());
    }

    public List<Code> findAllCached() {
        Object value = stringRedisTemplate.opsForHash().get(CACHE_KEY, CACHE_FIELD_ALL);
        if (value != null) {
            return JsonUtils.fromJson(value.toString(), new TypeToken<List<Code>>() {}.getType());
        }
        return codeRepository.findAllBy(new CodeSearchParam());
    }

    public String findNameByCode(String codeGroup, String code) {
        return findAllCached().stream()
                .filter(c -> codeGroup.equals(c.getCodeGroup()) && code.equals(c.getCode()))
                .map(Code::getName)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return codeRepository.existsById(id);
    }

    @Override
    public Optional<Code> findById(Long id) {
        return codeRepository.findById(id);
    }

    @Override
    public List<Code> findAllBy(CodeSearchParam param) {
        return codeRepository.findAllBy(param);
    }

    public Page<Code> findAllBy(CodeSearchParam param, Pageable pageable) {
        return codeRepository.findAllBy(param, pageable);
    }

    @Override
    public Code save(Code code) {
        if (!StringUtils.hasText(code.getCode())) {
            String maxCode = codeRepository.findMaxCodeByCodeGroupId(code.getCodeGroupId());
            code.setCode(nextCode(maxCode));
        }
        return codeRepository.save(code);
    }

    private String nextCode(String currentMax) {
        if (!StringUtils.hasText(currentMax)) {
            return "001";
        }
        int next = Integer.parseInt(currentMax) + 1;
        return String.format("%03d", next);
    }

    @Override
    public Code update(Code code) {
        return codeRepository.save(code);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) return;
        codeRepository.deleteById(id);
    }

    @Transactional
    public void deleteByCodeGroupId(Long groupId) {
        if (groupId == null) return;
        codeRepository.deleteByCodeGroupId(groupId);
    }
}

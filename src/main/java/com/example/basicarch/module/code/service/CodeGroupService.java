package com.example.default1.module.code.service;

import com.example.default1.base.service.BaseService;
import com.example.default1.module.code.entity.CodeGroup;
import com.example.default1.module.code.model.CodeGroupSearchParam;
import com.example.default1.module.code.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CodeGroupService implements BaseService<CodeGroup, CodeGroupSearchParam, Long> {
    private final CodeGroupRepository codeGroupRepository;

    @Override
    public boolean existsById(Long id) {
        return codeGroupRepository.existsById(id);
    }

    @Override
    public Optional<CodeGroup> findById(Long id) {
        return codeGroupRepository.findById(id);
    }

    public Optional<CodeGroup> findByIdWithCodes(Long id) {
        return codeGroupRepository.findWithCodesById(id);
    }

    @Override
    public List<CodeGroup> findAllBy(CodeGroupSearchParam param) {
        return codeGroupRepository.findAllBy(param);
    }

    @Override
    public CodeGroup save(CodeGroup codeGroup) {
        if (!StringUtils.hasText(codeGroup.getCodeGroup())) {
            String maxCodeGroup = codeGroupRepository.findMaxCodeGroup();
            codeGroup.setCodeGroup(nextCode(maxCodeGroup));
        }
        return codeGroupRepository.save(codeGroup);
    }

    private String nextCode(String currentMax) {
        if (!StringUtils.hasText(currentMax)) {
            return "001";
        }
        int next = Integer.parseInt(currentMax) + 1;
        return String.format("%03d", next);
    }

    @Override
    public CodeGroup update(CodeGroup codeGroup) {
        return codeGroupRepository.save(codeGroup);
    }

    @Override
    public void deleteById(Long id) {
        codeGroupRepository.deleteById(id);
    }
}

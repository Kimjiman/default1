package com.example.default1.module.code.service;

import com.example.default1.base.exception.CustomException;
import com.example.default1.base.service.BaseService;
import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeSearchParam;
import com.example.default1.module.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodeService implements BaseService<Code, CodeSearchParam, Long> {
    private final CodeRepository codeRepository;

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
        if (code.getCodeGroupId() == null) {
            throw new CustomException(2800, "code.code_group_id가 입력되지 않았습니다.");
        }
        if (code.getOrder() == null) {
            Integer maxOrder = codeRepository.findMaxOrderByCodeGroupId(code.getCodeGroupId());
            code.setOrder(maxOrder != null ? maxOrder + 1 : 1);
        }
        return codeRepository.save(code);
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

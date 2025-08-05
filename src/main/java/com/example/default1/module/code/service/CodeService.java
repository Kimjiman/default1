package com.example.default1.module.code.service;

import com.example.default1.base.service.BaseService;
import com.example.default1.exception.CustomException;
import com.example.default1.module.code.dto.Code;
import com.example.default1.module.code.dto.CodeSearchParam;
import com.example.default1.module.code.mapper.CodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService implements BaseService<Code, CodeSearchParam> {
    private final CodeMapper codeMapper;

    @Override
    public boolean existsById(Long id) {
        return this.findById(id) != null;
    }

    @Override
    public Code findById(Long id) {
        return codeMapper.findById(id);
    }

    @Override
    public Long countAllBy(CodeSearchParam codeSearchParam) {
        return codeMapper.countAllBy(codeSearchParam);
    }

    @Override
    public List<Code> findAllBy(CodeSearchParam codeSearchParam) {
        return codeMapper.findAllBy(codeSearchParam);
    }

    @Override
    public Long create(Code code) {
        if (code.isEmpty() || code.getCodeGroupId() == null) {
            throw new CustomException(2800, "code.code_group_id가 입력되지 않았습니다.");
        }
        codeMapper.create(code);
        return code.getId();
    }

    @Override
    public Long update(Code code) {
        if (code.isEmpty() || code.getId() == null) {
            throw new CustomException(2800, "code.code_group_id가 입력되지 않았습니다.");
        }
        codeMapper.update(code);
        return code.getId();
    }

    @Override
    public boolean removeById(Long id) {
        if (id == null) return false;
        return codeMapper.removeById(id) > 0;
    }

    public boolean removeByCodeGroupId(Long groupId) {
        if (groupId == null) return false;
        return codeMapper.removeByCodeGroupId(groupId) > 0;
    }
}

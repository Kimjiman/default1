package com.example.default1.module.code.service;

import com.example.default1.base.exception.CustomException;
import com.example.default1.base.service.BaseService;
import com.example.default1.module.code.dto.CodeDTO;
import com.example.default1.module.code.mapper.CodeMapper;
import com.example.default1.module.code.model.CodeSearchParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService implements BaseService<CodeDTO, CodeSearchParam, Long> {
    private final CodeMapper codeMapper;

    @Override
    public boolean existsById(Long id) {
        return this.findById(id) != null;
    }

    @Override
    public CodeDTO findById(Long id) {
        return codeMapper.findById(id);
    }

    @Override
    public Long countAllBy(CodeSearchParam codeSearchParam) {
        return codeMapper.countAllBy(codeSearchParam);
    }

    @Override
    public List<CodeDTO> findAllBy(CodeSearchParam codeSearchParam) {
        return codeMapper.findAllBy(codeSearchParam);
    }

    @Override
    public Long create(CodeDTO dto) {
        if (dto.isEmpty() || dto.getCodeGroupId() == null) {
            throw new CustomException(2800, "code.code_group_id가 입력되지 않았습니다.");
        }
        codeMapper.create(dto);
        return dto.getId();
    }

    @Override
    public Long update(CodeDTO dto) {
        if (dto.isEmpty() || dto.getId() == null) {
            throw new CustomException(2800, "code.code_group_id가 입력되지 않았습니다.");
        }
        codeMapper.update(dto);
        return dto.getId();
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

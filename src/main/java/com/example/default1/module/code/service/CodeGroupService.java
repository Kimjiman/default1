package com.example.default1.module.code.service;

import com.example.default1.base.service.BaseService;
import com.example.default1.module.code.model.CodeGroup;
import com.example.default1.module.code.model.CodeGroupSearchParam;
import com.example.default1.module.code.mapper.CodeGroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.default1.module.code
 * fileName       : CodeGroupService
 * author         : KIM JIMAN
 * date           : 25. 8. 5. 화요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 5.     KIM JIMAN      First Commit
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CodeGroupService implements BaseService<CodeGroup, CodeGroupSearchParam> {
    private final CodeGroupMapper codeGroupMapper;

    @Override
    public boolean existsById(Long id) {
        return this.findById(id) != null;
    }

    @Override
    public CodeGroup findById(Long id) {
        return codeGroupMapper.findById(id);
    }

    @Override
    public Long countAllBy(CodeGroupSearchParam codeGroupSearchParam) {
        return codeGroupMapper.countAllBy(codeGroupSearchParam);
    }

    @Override
    public List<CodeGroup> findAllBy(CodeGroupSearchParam codeGroupSearchParam) {
        return codeGroupMapper.findAllBy(codeGroupSearchParam);
    }

    @Override
    public Long create(CodeGroup codeGroup) {
        codeGroupMapper.create(codeGroup);
        return codeGroup.getId();
    }

    @Override
    public Long update(CodeGroup codeGroup) {
        codeGroupMapper.update(codeGroup);
        return codeGroup.getId();
    }

    @Override
    public boolean removeById(Long id) {
        return codeGroupMapper.removeById(id) > 0;
    }
}

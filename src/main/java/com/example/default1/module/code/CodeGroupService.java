package com.example.default1.module.code;

import com.example.default1.base.service.BaseService;
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
@Service
@Slf4j
public class CodeGroupService implements BaseService<CodeGroup, CodeGroupSearchParam> {
    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public CodeGroup findById(Long id) {
        return null;
    }

    @Override
    public Long countAllBy(CodeGroupSearchParam codeGroupSearchParam) {
        return 0L;
    }

    @Override
    public List<CodeGroup> findAllBy(CodeGroupSearchParam codeGroupSearchParam) {
        return List.of();
    }

    @Override
    public CodeGroup create(CodeGroup codeGroup) {
        return null;
    }

    @Override
    public CodeGroup update(CodeGroup codeGroup) {
        return null;
    }

    @Override
    public boolean removeById(Long id) {
        return false;
    }
}

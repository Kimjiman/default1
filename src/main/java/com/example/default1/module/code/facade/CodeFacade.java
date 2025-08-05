package com.example.default1.module.code.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.module.code.dto.Code;
import com.example.default1.module.code.dto.CodeGroup;
import com.example.default1.module.code.dto.CodeGroupSearchParam;
import com.example.default1.module.code.dto.CodeSearchParam;
import com.example.default1.module.code.service.CodeGroupService;
import com.example.default1.module.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.example.default1.module.code.facade
 * fileName       : CodeFacade
 * author         : KIM JIMAN
 * date           : 25. 8. 5. 화요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 5.     KIM JIMAN      First Commit
 */
@Facade
@RequiredArgsConstructor
@Service
public class CodeFacade {
    private final CodeGroupService codeGroupService;
    private final CodeService codeService;

    public List<CodeGroup> findAllBy(CodeGroupSearchParam param) {
        List<CodeGroup> codeGroupList = codeGroupService.findAllBy(param);
        return codeGroupList.stream()
                .peek(it -> {
                    it.setCodeList(codeService.findAllBy(CodeSearchParam.builder()
                            .codeGroupId(it.getId())
                            .build()));
                })
                .collect(Collectors.toList());
    }

    public CodeGroup findBy(Long id) {
        if (id == null) return null;
        CodeGroup codeGroup = codeGroupService.findById(id);
        if (codeGroup != null) {
            codeGroup.setCodeList(codeService.findAllBy(CodeSearchParam.builder().codeGroupId(id).build()));
        }
        return codeGroup;
    }

    @Transactional
    public void removeById(Long id) {
        if (id == null) return;
        codeGroupService.removeById(id);
        codeService.removeById(id);
    }
}

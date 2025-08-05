package com.example.default1.module.code.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.module.code.dto.Code;
import com.example.default1.module.code.dto.CodeGroup;
import com.example.default1.module.code.dto.CodeGroupSearchParam;
import com.example.default1.module.code.dto.CodeSearchParam;
import com.example.default1.module.code.service.CodeGroupService;
import com.example.default1.module.code.service.CodeService;
import lombok.RequiredArgsConstructor;
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
public class CodeFacade {
    private final CodeGroupService codeGroupService;
    private final CodeService codeService;

    public List<CodeGroup> findCodeGroupAllBy(CodeGroupSearchParam param) {
        List<CodeGroup> codeGroupList = codeGroupService.findAllBy(param);
        return codeGroupList.stream()
                .peek(it -> {
                    it.setCodeList(codeService.findAllBy(CodeSearchParam.builder()
                            .codeGroupId(it.getId())
                            .build()));
                })
                .collect(Collectors.toList());
    }


    public CodeGroup findCodeGroupById(Long id) {
        if (id == null) return null;
        CodeGroup codeGroup = codeGroupService.findById(id);
        if (codeGroup != null) {
            codeGroup.setCodeList(codeService.findAllBy(CodeSearchParam.builder().codeGroupId(id).build()));
        }
        return codeGroup;
    }

    public void createCodeGroup(CodeGroup codeGroup) {
        codeGroup.setCurrentUser();
        codeGroupService.create(codeGroup);
    }

    public void updateCodeGroup(CodeGroup codeGroup) {
        codeGroup.setCurrentUserUpdateId();
        codeGroupService.update(codeGroup);
    }

    @Transactional
    public void removeCodeGroupById(Long id) {
        if (id == null) return;
        codeService.removeByCodeGroupId(id);
        codeGroupService.removeById(id);
    }

    public List<Code> findCodeAllBy(CodeSearchParam param) {
        return codeService.findAllBy(param);
    }

    public Code findCodeById(Long id) {
        if (id == null) return null;
        return codeService.findById(id);
    }

    public void createCode(Code code) {
        code.setCurrentUser();
        codeService.create(code);
    }

    public void updateCode(Code code) {
        code.setCurrentUserUpdateId();
        codeService.update(code);
    }

    public void removeCodeById(Long id) {
        if (id == null) return;
        codeService.removeById(id);
    }
}

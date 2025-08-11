package com.example.default1.module.code.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.module.code.converter.CodeConverter;
import com.example.default1.module.code.converter.CodeGroupConverter;
import com.example.default1.module.code.dto.CodeDTO;
import com.example.default1.module.code.dto.CodeGroupDTO;
import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeGroup;
import com.example.default1.module.code.model.CodeGroupSearchParam;
import com.example.default1.module.code.model.CodeSearchParam;
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
    private final CodeGroupConverter codeGroupConverter;
    private final CodeConverter codeConverter;

    public List<CodeGroupDTO> findCodeGroupAllBy(CodeGroupSearchParam param) {
        List<CodeGroup> codeGroupList = codeGroupService.findAllBy(param);

        return codeGroupList.stream()
                .peek(it -> {
                    it.setCodeList(codeService.findAllBy(CodeSearchParam.builder()
                            .codeGroupId(it.getId())
                            .build()));
                })
                .map(codeGroup -> codeGroupConverter.toDto(codeGroup))
                .collect(Collectors.toList());
    }


    public CodeGroupDTO findCodeGroupById(Long id) {
        if (id == null) return null;
        CodeGroup codeGroup = codeGroupService.findById(id);
        if (codeGroup != null) {
            codeGroup.setCodeList(codeService.findAllBy(CodeSearchParam.builder().codeGroupId(id).build()));
        }

        return codeGroupConverter.toDto(codeGroup);
    }

    public void createCodeGroup(CodeGroup codeGroup) {
        codeGroupService.create(codeGroup);
    }

    public void updateCodeGroup(CodeGroup codeGroup) {
        codeGroupService.update(codeGroup);
    }

    @Transactional
    public void removeCodeGroupById(Long id) {
        if (id == null) return;
        codeService.removeByCodeGroupId(id);
        codeGroupService.removeById(id);
    }

    public List<CodeDTO> findCodeAllBy(CodeSearchParam param) {
        return codeService.findAllBy(param).stream()
                .map(it -> codeConverter.toDto(it))
                .collect(Collectors.toList());
    }

    public CodeDTO findCodeById(Long id) {
        if (id == null) return null;
        return codeConverter.toDto(codeService.findById(id));
    }

    public void createCode(Code code) {
        codeService.create(code);
    }

    public void updateCode(Code code) {
        codeService.update(code);
    }

    public void removeCodeById(Long id) {
        if (id == null) return;
        codeService.removeById(id);
    }
}

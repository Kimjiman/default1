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
import org.jetbrains.annotations.NotNull;
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

    public List<CodeGroup> findCodeGroupAllBy(CodeGroupSearchParam param) {
        return codeGroupService.findAllBy(param).stream()
                .map(codeGroupConverter::fromDto)
                .toList();
    }

    public CodeGroup findCodeGroupById(@NotNull Long id) {
        CodeGroupDTO dto = codeGroupService.findById(id);
        if (dto != null) {
            dto.setCodeDtoList(codeService.findAllBy(CodeSearchParam.builder().codeGroupId(id).build()));
        }
        return codeGroupConverter.fromDto(dto);
    }

    public void createCodeGroup(CodeGroup codeGroup) {
        codeGroupService.create(codeGroupConverter.toDto(codeGroup));
    }

    public void updateCodeGroup(CodeGroup codeGroup) {
        codeGroupService.update(codeGroupConverter.toDto(codeGroup));
    }

    @Transactional
    public void removeCodeGroupById(Long id) {
        if (id == null) return;
        codeService.removeByCodeGroupId(id);
        this.removeCodeById(id);
    }

    public List<Code> findCodeAllBy(CodeSearchParam param) {
        return codeService.findAllBy(param).stream()
                .map(codeConverter::fromDto)
                .toList();
    }

    public Code findCodeById(Long id) {
        if (id == null) return null;
        return codeConverter.fromDto(codeService.findById(id));
    }

    public void createCode(Code code) {
        codeService.create(codeConverter.toDto(code));
    }

    public void updateCode(Code code) {
        codeService.update(codeConverter.toDto(code));
    }

    public void removeCodeById(Long id) {
        if (id == null) return;
        codeService.removeById(id);
    }
}

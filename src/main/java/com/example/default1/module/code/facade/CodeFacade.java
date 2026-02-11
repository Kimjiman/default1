package com.example.default1.module.code.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeGroup;
import com.example.default1.module.code.model.CodeGroupSearchParam;
import com.example.default1.module.code.model.CodeSearchParam;
import com.example.default1.module.code.service.CodeGroupService;
import com.example.default1.module.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class CodeFacade {
    private final CodeGroupService codeGroupService;
    private final CodeService codeService;

    public List<CodeGroup> findCodeGroupAllBy(CodeGroupSearchParam param) {
        return codeGroupService.findAllBy(param);
    }

    public CodeGroup findCodeGroupById(Long id) {
        CodeGroup codeGroup = codeGroupService.findById(id).orElse(null);
        if (codeGroup != null) {
            codeGroup.setCodeList(codeService.findAllBy(CodeSearchParam.builder().codeGroupId(id).build()));
        }
        return codeGroup;
    }

    public void createCodeGroup(CodeGroup codeGroup) {
        codeGroupService.save(codeGroup);
    }

    public void updateCodeGroup(CodeGroup codeGroup) {
        codeGroupService.save(codeGroup);
    }

    @Transactional
    public void removeCodeGroupById(Long id) {
        if (id == null) return;
        codeService.deleteByCodeGroupId(id);
        codeGroupService.deleteById(id);
    }

    public List<Code> findCodeAllBy(CodeSearchParam param) {
        return codeService.findAllBy(param);
    }

    public Code findCodeById(Long id) {
        if (id == null) return null;
        return codeService.findById(id).orElse(null);
    }

    public void createCode(Code code) {
        codeService.save(code);
    }

    public void updateCode(Code code) {
        codeService.save(code);
    }

    public void removeCodeById(Long id) {
        if (id == null) return;
        codeService.deleteById(id);
    }
}

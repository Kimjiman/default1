package com.example.default1.module.code.facade;

import com.example.default1.base.annotation.Facade;
import com.example.default1.module.code.converter.CodeConverter;
import com.example.default1.module.code.converter.CodeGroupConverter;
import com.example.default1.module.code.model.CodeGroupModel;
import com.example.default1.module.code.model.CodeGroupSearchParam;
import com.example.default1.module.code.model.CodeModel;
import com.example.default1.module.code.model.CodeSearchParam;
import com.example.default1.base.model.pager.PageResponse;
import com.example.default1.module.code.service.CodeGroupService;
import com.example.default1.module.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class CodeFacade {
    private final CodeGroupService codeGroupService;
    private final CodeService codeService;
    private final CodeConverter codeConverter;
    private final CodeGroupConverter codeGroupConverter;

    public List<CodeGroupModel> findCodeGroupAllBy(CodeGroupSearchParam param) {
        return codeGroupConverter.toModelList(codeGroupService.findAllBy(param));
    }

    public CodeGroupModel findCodeGroupById(Long id) {
        return codeGroupService.findByIdWithCodes(id)
                .map(codeGroupConverter::toModel)
                .orElse(null);
    }

    public void createCodeGroup(CodeGroupModel codeGroupModel) {
        codeGroupService.save(codeGroupConverter.toEntity(codeGroupModel));
    }

    public void updateCodeGroup(CodeGroupModel codeGroupModel) {
        codeGroupService.save(codeGroupConverter.toEntity(codeGroupModel));
    }

    @Transactional
    public void removeCodeGroupById(Long id) {
        if (id == null) return;
        codeService.deleteByCodeGroupId(id);
        codeGroupService.deleteById(id);
    }

    public List<CodeModel> findCodeAllBy(CodeSearchParam param) {
        return codeConverter.toModelList(codeService.findAllBy(param));
    }

    public PageResponse<CodeModel> findCodeAllBy(CodeSearchParam param, Pageable pageable) {
        return codeConverter.toPageResponse(codeService.findAllBy(param, pageable));
    }

    public CodeModel findCodeById(Long id) {
        if (id == null) return null;
        return codeService.findById(id)
                .map(codeConverter::toModel)
                .orElse(null);
    }

    public void createCode(CodeModel codeModel) {
        codeService.save(codeConverter.toEntity(codeModel));
    }

    public void updateCode(CodeModel codeModel) {
        codeService.save(codeConverter.toEntity(codeModel));
    }

    public void removeCodeById(Long id) {
        if (id == null) return;
        codeService.deleteById(id);
    }
}

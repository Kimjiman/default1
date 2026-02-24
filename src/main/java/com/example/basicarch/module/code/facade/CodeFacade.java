package com.example.basicarch.module.code.facade;

import com.example.basicarch.base.annotation.Facade;
import com.example.basicarch.base.constants.CacheType;
import com.example.basicarch.base.exception.SystemErrorCode;
import com.example.basicarch.base.exception.ToyAssert;
import com.example.basicarch.base.redis.CacheEventPublishable;
import lombok.Getter;
import com.example.basicarch.module.code.converter.CodeConverter;
import com.example.basicarch.module.code.converter.CodeGroupConverter;
import com.example.basicarch.module.code.model.CodeGroupModel;
import com.example.basicarch.module.code.model.CodeGroupSearchParam;
import com.example.basicarch.module.code.model.CodeModel;
import com.example.basicarch.module.code.model.CodeSearchParam;
import com.example.basicarch.base.model.pager.PageResponse;
import com.example.basicarch.module.code.service.CodeGroupService;
import com.example.basicarch.module.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class CodeFacade implements CacheEventPublishable {
    private final CodeGroupService codeGroupService;
    private final CodeService codeService;
    private final CodeConverter codeConverter;
    private final CodeGroupConverter codeGroupConverter;
    @Getter
    private final CacheEventPublishable.Publisher cacheEventPublisher;

    @Override
    public CacheType getCacheType() {
        return CacheType.CODE;
    }

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
        publishCacheEvent();
    }

    public void updateCodeGroup(CodeGroupModel codeGroupModel) {
        codeGroupService.update(codeGroupConverter.toEntity(codeGroupModel));
        publishCacheEvent();
    }

    @Transactional
    public void removeCodeGroupById(Long id) {
        ToyAssert.notNull(id, SystemErrorCode.REQUIRED, "ID를 입력해주세요.");
        codeService.deleteByCodeGroupId(id);
        codeGroupService.deleteById(id);
        publishCacheEvent();
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
        ToyAssert.notNull(codeModel.getCodeGroupId(), SystemErrorCode.REQUIRED, "code_group_id가 입력되지 않았습니다.");
        codeService.save(codeConverter.toEntity(codeModel));
        publishCacheEvent();
    }

    public void updateCode(CodeModel codeModel) {
        codeService.update(codeConverter.toEntity(codeModel));
        publishCacheEvent();
    }

    public void removeCodeById(Long id) {
        ToyAssert.notNull(id, SystemErrorCode.REQUIRED, "ID를 입력해주세요.");
        codeService.deleteById(id);
        publishCacheEvent();
    }
}

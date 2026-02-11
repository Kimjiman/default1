package com.example.default1.module.code.repository;

import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeSearchParam;

import java.util.List;

public interface CodeRepositoryCustom {
    Long countAllBy(CodeSearchParam param);
    List<Code> findAllBy(CodeSearchParam param);
    Integer findMaxOrderByCodeGroupId(Long codeGroupId);
}

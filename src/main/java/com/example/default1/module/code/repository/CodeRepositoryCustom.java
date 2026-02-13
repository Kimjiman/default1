package com.example.default1.module.code.repository;

import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeSearchParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CodeRepositoryCustom {
    List<Code> findAllBy(CodeSearchParam param);
    Page<Code> findAllBy(CodeSearchParam param, Pageable pageable);
    String findMaxCodeByCodeGroupId(Long codeGroupId);
}

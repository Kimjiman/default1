package com.example.default1.module.code.repository;

import com.example.default1.module.code.entity.CodeGroup;
import com.example.default1.module.code.model.CodeGroupSearchParam;

import java.util.List;

public interface CodeGroupRepositoryCustom {
    List<CodeGroup> findAllBy(CodeGroupSearchParam param);
    String findMaxCodeGroup();
}

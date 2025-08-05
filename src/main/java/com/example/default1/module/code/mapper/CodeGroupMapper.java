package com.example.default1.module.code.mapper;

import com.example.default1.module.code.dto.Code;
import com.example.default1.module.code.dto.CodeGroup;
import com.example.default1.module.code.dto.CodeGroupSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeGroupMapper {
    Long countAllBy(CodeGroupSearchParam codeGroupSearchParam);
    List<CodeGroup> findAllBy(CodeGroupSearchParam codeGroupSearchParam);
    CodeGroup findById(Long id);
    void create(CodeGroup codeGroup);
    void update(CodeGroup codeGroup);
    int removeById(Long id);
}

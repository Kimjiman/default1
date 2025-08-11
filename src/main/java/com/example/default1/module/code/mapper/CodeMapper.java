package com.example.default1.module.code.mapper;

import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
    Long countAllBy(CodeSearchParam codeSearchParam);
    List<Code> findAllBy(CodeSearchParam codeSearchParam);
    Code findById(Long id);
    void create(Code code);
    void update(Code code);
    int removeById(Long id);
    int removeByCodeGroupId(Long id);
}

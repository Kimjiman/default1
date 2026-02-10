package com.example.default1.module.code.mapper;

import com.example.default1.base.mapper.BaseMapper;
import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper extends BaseMapper<Code, CodeSearchParam, Long> {
    int removeByCodeGroupId(Long id);
}

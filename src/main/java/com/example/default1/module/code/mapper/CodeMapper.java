package com.example.default1.module.code.mapper;

import com.example.default1.base.mapper.BaseMapper;
import com.example.default1.module.code.dto.CodeDTO;
import com.example.default1.module.code.model.Code;
import com.example.default1.module.code.model.CodeSearchParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeMapper extends BaseMapper<CodeDTO, CodeSearchParam, Long> {
    int removeByCodeGroupId(Long id);
}

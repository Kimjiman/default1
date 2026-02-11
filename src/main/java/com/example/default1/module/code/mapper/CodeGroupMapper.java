package com.example.default1.module.code.mapper;

import com.example.default1.base.mapper.BaseMapper;
import com.example.default1.module.code.dto.CodeGroupDTO;
import com.example.default1.module.code.model.CodeGroup;
import com.example.default1.module.code.model.CodeGroupSearchParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeGroupMapper extends BaseMapper<CodeGroupDTO, CodeGroupSearchParam, Long> {

}

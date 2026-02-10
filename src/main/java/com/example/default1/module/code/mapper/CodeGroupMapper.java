package com.example.default1.module.code.mapper;

import com.example.default1.base.mapper.BaseMapper;
import com.example.default1.module.code.model.CodeGroup;
import com.example.default1.module.code.model.CodeGroupSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeGroupMapper extends BaseMapper<CodeGroup, CodeGroupSearchParam, Long> {

}

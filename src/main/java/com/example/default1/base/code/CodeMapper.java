package com.example.default1.base.code;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
    List<CodeGroup> selectCodeGroupList(CodeGroup codeGroup);
    CodeGroup selectCodeGroupById(Long id);
    List<Code> selectCodeList(Code code);
    Code selectCodeById(Long id);
    void insertCodeGroup(CodeGroup codeGroup);
    void insertCode(Code code);
    void updateCodeGroup(CodeGroup codeGroup);
    void updateCode(Code code);
    void deleteCodeGroupById(Long id);
    void deleteCodeByCodeGroupId(Long codeGroupId);
    void deleteCodeById(Long id);
}

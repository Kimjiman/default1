package com.example.default1.base.code;

import com.example.default1.exception.CustomException;
import com.example.default1.utils.SessionUtils;
import com.example.default1.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeMapper codeMapper;

    public List<CodeGroup> selectCodeGroupList(CodeGroup codeGroup) {
        List<CodeGroup> codeGroupList = codeMapper.selectCodeGroupList(codeGroup);
        codeGroupList.forEach(it -> {
            Code code = new Code();
            code.setCodeGroupId(it.getId());
            it.setCodeList(codeMapper.selectCodeList(code));
        });
        return codeGroupList;
    }

    public CodeGroup selectCodeGroupById(Long id) {
        if(id == null) return null;
        CodeGroup codeGroup = codeMapper.selectCodeGroupById(id);
        if(codeGroup != null) {
            Code code = new Code();
            code.setCodeGroupId(id);
            codeGroup.setCodeList(codeMapper.selectCodeList(code));
        }
        return codeGroup;
    }

    public List<Code> selectCodeList(Code code) {
        return codeMapper.selectCodeList(code);
    }

    public Code selectCodeById(Long id) {
        if(id == null) return null;
        return codeMapper.selectCodeById(id);
    }

    public void createCodeGroup(CodeGroup codeGroup) {
        if(StringUtils.isBlank(codeGroup.getName())) {
            throw new CustomException(2800, "codeGroup.name이 입력되지 않았습니다.");
        }
        codeGroup.setCreateId(SessionUtils.getUserId());
        codeGroup.setUpdateId(SessionUtils.getUserId());
        codeMapper.insertCodeGroup(codeGroup);
    }

    public void createCode(Code code) {
        if(code.getCodeGroupId() == null) {
            throw new CustomException(2800, "code.code_group_id가 입력되지 않았습니다.");
        }

        CodeGroup codeGroup = codeMapper.selectCodeGroupById(code.getCodeGroupId());
        if(codeGroup == null) {
            throw new CustomException(2800, "code.code_group_id에 해당하는 그룹이 없습니다.");
        }
        if(StringUtils.isBlank(codeGroup.getName())) {
            throw new CustomException(2800, "code.name이 입력되지 않았습니다.");
        }
        code.setCreateId(SessionUtils.getUserId());
        code.setUpdateId(SessionUtils.getUserId());
        codeMapper.insertCode(code);
    }

    public void updateCodeGroup(CodeGroup codeGroup) {
        if(codeGroup.getId() == null) return;
        codeGroup.setUpdateId(SessionUtils.getUserId());
        codeMapper.updateCodeGroup(codeGroup);
    }

    public void updateCode(Code code) {
        if(code.getId() == null) return;
        code.setUpdateId(SessionUtils.getUserId());
        codeMapper.updateCode(code);
    }

    @Transactional
    public void deleteCodeGroupById(Long id) {
        if(id == null) return;
        codeMapper.deleteCodeByCodeGroupId(id);
        codeMapper.deleteCodeGroupById(id);
    }

    public void deleteCodeById(Long id) {
        if(id == null) return;
        codeMapper.deleteCodeById(id);
    }

}

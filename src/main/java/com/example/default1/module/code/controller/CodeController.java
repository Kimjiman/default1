package com.example.default1.module.code.controller;

import com.example.default1.module.code.dto.Code;
import com.example.default1.module.code.dto.CodeGroup;
import com.example.default1.module.code.dto.CodeGroupSearchParam;
import com.example.default1.module.code.dto.CodeSearchParam;
import com.example.default1.module.code.facade.CodeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {
    private final CodeFacade codeFacade;

    @GetMapping("/codeGroup")
    public List<CodeGroup> selectCodeGroupList(CodeGroupSearchParam param) {
        return codeFacade.findCodeGroupAllBy(param);
    }

    @GetMapping("/code")
    public List<Code> selectCodeList(CodeSearchParam param) {
        return codeFacade.findCodeAllBy(param);
    }

    @GetMapping("/codeGroup/{id}")
    public CodeGroup selectCodeGroupById(@PathVariable Long id) {
        return codeFacade.findCodeGroupById(id);
    }

    @GetMapping("/code/{id}")
    public Code selectCodeById(@PathVariable Long id) {
        return codeFacade.findCodeById(id);
    }

    @PostMapping("/codeGroup")
    public void createCodeGroup(@RequestBody CodeGroup codeGroup) {
        codeFacade.createCodeGroup(codeGroup);
    }

    @PostMapping("/code")
    public void createCode(@RequestBody Code code) {
        codeFacade.createCode(code);
    }

    @PutMapping("/codeGroup")
    public void updateCodeGroup(@RequestBody CodeGroup codeGroup) {
        codeFacade.updateCodeGroup(codeGroup);
    }

    @PutMapping("/code")
    public void updateCode(@RequestBody Code code) {
        codeFacade.updateCode(code);
    }

    @DeleteMapping("/codeGroup/{id}")
    public void deleteCodeGroup(@PathVariable Long id) {
        codeFacade.removeCodeGroupById(id);
    }

    @DeleteMapping("/code/{id}")
    public void deleteCode(@PathVariable Long id) {
        codeFacade.removeCodeById(id);
    }
}

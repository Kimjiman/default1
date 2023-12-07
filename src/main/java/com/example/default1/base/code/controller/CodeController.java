package com.example.default1.base.code.controller;

import com.example.default1.base.code.model.Code;
import com.example.default1.base.code.model.CodeGroup;
import com.example.default1.base.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {
    private final CodeService codeService;

    @GetMapping("/codeGroup")
    @ResponseBody
    public List<CodeGroup> selectCodeGroupList(CodeGroup codeGroup) {
        return codeService.selectCodeGroupList(codeGroup);
    }

    @GetMapping("/code")
    @ResponseBody
    public List<Code> selectCodeList(Code code) {
        return codeService.selectCodeList(code);
    }

    @GetMapping("/codeGroup/{id}")
    @ResponseBody
    public CodeGroup selectCodeGroupById(@PathVariable Long id) {
        return codeService.selectCodeGroupById(id);
    }

    @GetMapping("/code/{id}")
    @ResponseBody
    public Code selectCodeById(@PathVariable Long id) {
        return codeService.selectCodeById(id);
    }

    @PostMapping("/codeGroup")
    @ResponseBody
    public void createCodeGroup(@RequestBody CodeGroup codeGroup) {
        codeService.createCodeGroup(codeGroup);
    }

    @PostMapping("/code")
    @ResponseBody
    public void createCode(@RequestBody Code code) {
        codeService.createCode(code);
    }

    @PutMapping("/codeGroup")
    @ResponseBody
    public void updateCodeGroup(@RequestBody CodeGroup codeGroup) {
        codeService.updateCodeGroup(codeGroup);
    }

    @PutMapping("/code")
    @ResponseBody
    public void updateCode(@RequestBody Code code) {
        codeService.updateCode(code);
    }

    @DeleteMapping("/codeGroup/{id}")
    @ResponseBody
    public void deleteCodeGroup(@PathVariable Long id) {
        codeService.deleteCodeGroupById(id);
    }

    @DeleteMapping("/code/{id}")
    @ResponseBody
    public void deleteCode(@PathVariable Long id) {
        codeService.deleteCodeById(id);
    }
}

package com.example.default1.base.code;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {
    private final CodeService codeService;

    @GetMapping("/codeGroup")
    public List<CodeGroup> selectCodeGroupList(CodeGroup codeGroup) {
        return codeService.selectCodeGroupList(codeGroup);
    }

    @GetMapping("/code")
    public List<Code> selectCodeList(Code code) {
        return codeService.selectCodeList(code);
    }

    @GetMapping("/codeGroup/{id}")
    public CodeGroup selectCodeGroupById(@PathVariable Long id) {
        return codeService.selectCodeGroupById(id);
    }

    @GetMapping("/code/{id}")
    public Code selectCodeById(@PathVariable Long id) {
        return codeService.selectCodeById(id);
    }

    @PostMapping("/codeGroup")
    public void createCodeGroup(@RequestBody CodeGroup codeGroup) {
        codeService.createCodeGroup(codeGroup);
    }

    @PostMapping("/code")
    public void createCode(@RequestBody Code code) {
        codeService.createCode(code);
    }

    @PutMapping("/codeGroup")
    public void updateCodeGroup(@RequestBody CodeGroup codeGroup) {
        codeService.updateCodeGroup(codeGroup);
    }

    @PutMapping("/code")
    public void updateCode(@RequestBody Code code) {
        codeService.updateCode(code);
    }

    @DeleteMapping("/codeGroup/{id}")
    @ResponseBody
    public void deleteCodeGroup(@PathVariable Long id) {
        codeService.deleteCodeGroupById(id);
    }

    @DeleteMapping("/code/{id}")
    public void deleteCode(@PathVariable Long id) {
        codeService.deleteCodeById(id);
    }
}

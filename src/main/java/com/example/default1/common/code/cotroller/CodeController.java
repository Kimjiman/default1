package com.example.default1.common.code.cotroller;

import com.example.default1.common.code.model.Code;
import com.example.default1.common.code.model.CodeGroup;
import com.example.default1.common.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {
    private final CodeService codeService;

    @GetMapping("/codeGroup")
    @ResponseBody
    public ResponseEntity<?> selectCodeGroupList(CodeGroup codeGroup) {
        return ResponseEntity.ok(codeService.selectCodeGroupList(codeGroup));
    }

    @GetMapping("/code")
    @ResponseBody
    public ResponseEntity<?> selectCodeList(Code code) {
        return ResponseEntity.ok(codeService.selectCodeList(code));
    }

    @GetMapping("/codeGroup/{id}")
    @ResponseBody
    public ResponseEntity<?> selectCodeGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(codeService.selectCodeGroupById(id));
    }

    @GetMapping("/code/{id}")
    @ResponseBody
    public ResponseEntity<?> selectCodeById(@PathVariable Long id) {
        return ResponseEntity.ok(codeService.selectCodeById(id));
    }

    @PostMapping("/codeGroup")
    @ResponseBody
    public ResponseEntity<?> createCodeGroup(CodeGroup codeGroup) {
        codeService.createCodeGroup(codeGroup);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/code")
    @ResponseBody
    public ResponseEntity<?> createCode(Code code) {
        codeService.createCode(code);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/codeGroup")
    @ResponseBody
    public ResponseEntity<?> updateCodeGroup(CodeGroup codeGroup) {
        codeService.updateCodeGroup(codeGroup);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/code")
    @ResponseBody
    public ResponseEntity<?> updateCode(Code code) {
        codeService.updateCode(code);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/codeGroup/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCodeGroup(@PathVariable Long id) {
        codeService.deleteCodeGroupById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/code/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCode(@PathVariable Long id) {
        codeService.deleteCodeById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

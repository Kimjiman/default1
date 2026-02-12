package com.example.default1.module.test.controller;

import com.example.default1.base.exception.CustomException;
import com.example.default1.base.exception.SystemErrorCode;
import com.example.default1.module.test.repository.TestRepository;
import com.example.default1.module.test.model.TestModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestRepository testRepository;

    @GetMapping("/test")
    public ResponseEntity<?> test(TestModel testModel) {
        if(testModel.getId() == 1) {
            throw new CustomException(SystemErrorCode.INTERNAL_ERROR, "테스트");
        }
        return ResponseEntity.ok(testRepository.findAll());
    }

    @PostMapping("/test")
    public ResponseEntity<?> test1(@RequestBody TestModel testModel) {
        log.info("testModel: {}", testModel);
        testRepository.save(testModel);
        return ResponseEntity.ok(testRepository.findAll());
    }

    @PostMapping("/test2")
    public ResponseEntity<?> test2() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

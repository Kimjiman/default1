package com.example.default1.module.test.controller;

import com.example.default1.exception.CustomException;
import com.example.default1.module.test.mapper.TestMapper;
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
    private final TestMapper testMapper;

    @GetMapping("/test")
    public ResponseEntity<?> test(TestModel testModel) {
        if(testModel.getId() == 1) {
            throw new CustomException(2023, "테스트");
        }
//        log.info("testModel: {}", testModel);
        return ResponseEntity.ok(testMapper.testSql());
    }

    @PostMapping("/test")
    public ResponseEntity<?> test1(@RequestBody TestModel testModel) {
        log.info("testModel: {}", testModel);
        testMapper.testInsert(testModel);
        return ResponseEntity.ok(testMapper.testSql());
    }

    @PostMapping("/test2")
    public ResponseEntity<?> test2() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

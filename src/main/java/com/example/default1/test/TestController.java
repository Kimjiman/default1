package com.example.default1.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final TestMapper testMapper;

    @GetMapping("/test")
    public ResponseEntity<?> test(TestModel testModel) {
        log.info("testModel: {}", testModel);
        //testMapper.testInsert(testModel);
        return ResponseEntity.ok(testMapper.testSql());
    }

    @PostMapping("/test")
    public ResponseEntity<?> test1(@RequestBody TestModel testModel) {
        log.info("testModel: {}", testModel);
        //testMapper.testInsert(testModel);
        return ResponseEntity.ok(testMapper.testSql());
    }
}

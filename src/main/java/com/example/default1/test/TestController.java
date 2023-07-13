package com.example.default1.test;

import com.example.default1.exception.CustomException;
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
        if(testModel.getId() == 1) {
            throw new CustomException(2023, "테스트");
        }
        log.info("testModel: {}", testModel);
        return ResponseEntity.ok(testMapper.testSql());
    }

    @PostMapping("/test")
    public ResponseEntity<?> test1(@RequestBody TestModel testModel) {
        log.info("testModel: {}", testModel);
        testMapper.testInsert(testModel);
        return ResponseEntity.ok(testMapper.testSql());
    }
}

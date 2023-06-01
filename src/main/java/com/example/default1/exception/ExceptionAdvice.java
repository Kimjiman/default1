package com.example.default1.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<?> customException(CustomException ex) {
        return ResponseEntity.status(ex.getStatus()).body(Map.of(ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> BadRequestException(final RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}

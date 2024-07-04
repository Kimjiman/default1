package com.example.default1.config;

import com.example.default1.base.model.Response;
import com.example.default1.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler({Exception.class})
    public Response<?> InternalServerException(Exception ex) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler({CustomException.class})
    public Response<?> customException(CustomException ex) {
        return Response.fail(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public Response<?> BadRequestException(RuntimeException ex) {
        return Response.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public Response<?> DataIntegrityViolationException(DataIntegrityViolationException ex) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getCause().getMessage());
    }
}

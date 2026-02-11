package com.example.default1.base.exception;

import lombok.Getter;
import lombok.Setter;

// 에러코드 2000 ~ 2999 까지 사용
@Getter
@Setter
public class CustomException extends BaseException {
    private int status;
    private String message;

    public CustomException(int status, String message, Exception e) {
        super(status, message, e);
        this.status = status;
        this.message = message;
    }
    
    public CustomException(int status, String message) {
        super(status, message);
        this.status = status;
        this.message = message;
    }

    public CustomException(String message) {
        super(500, message);
        this.message = message;
    }
}

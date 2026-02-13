package com.example.default1.base.component.webclient;

import lombok.Getter;

@Getter
public class WebClientRequestException extends RuntimeException {

    private final int statusCode;

    public WebClientRequestException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}

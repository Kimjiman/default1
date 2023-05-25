package com.example.default1.exception;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 2288443710582826194L;

    public final int status;
    
    public BaseException(int status, String message) {
        super(message);
        this.status = status;
    }
    
    public BaseException(int status, String message, String appendMesage) {
        super(message + (null == appendMesage ? "" : appendMesage));
        this.status = status;
    }
    
    public BaseException(int status, String message, Exception e) {
        super(message, e);
        this.status = status;
    }
    
    public BaseException(int status, String message, String appendMesage, Exception e) {
        super(message + (null == appendMesage ? "" : appendMesage), e);
        this.status = status;
    }
}

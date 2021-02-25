package com.mvc.json.mock.api.test.exception;

public class ApiException extends Exception{
    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, StackTraceElement[] stackTrace) {
        super(msg);
        if (stackTrace != null) super.setStackTrace(stackTrace);
    }
}

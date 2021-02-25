package com.mvc.json.mock.api.test.exception;

public class ApiRunException extends ApiException {

    public ApiRunException(String msg) {
        super(msg);
    }

    public ApiRunException(String msg, StackTraceElement[] stackTrace) {
        super(msg, stackTrace);
    }
}

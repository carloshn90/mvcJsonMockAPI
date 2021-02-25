package com.mvc.json.mock.api.test.enums;

import com.mvc.json.mock.api.test.core.executors.*;
import lombok.Getter;

@Getter
public enum MethodEnum {
    GET("get", new GetTestExecutor()),
    POST("post", new PostTestExecutor()),
    PUT("put", new PutTestExecutor()),
    PATCH("patch", new PatchTestExecutor()),
    DELETE("delete", new DeleteTestExecutor());

    private String name;

    private TestExecutor executor;

    MethodEnum(String name, TestExecutor executor) {
        this.name = name;
        this.executor = executor;
    }
}

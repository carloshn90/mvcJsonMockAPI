package com.mvc.json.mock.api.test.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Test
public @interface TestEndPoint {

    String name();
}

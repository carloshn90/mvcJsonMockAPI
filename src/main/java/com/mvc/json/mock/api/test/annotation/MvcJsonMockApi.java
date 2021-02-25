package com.mvc.json.mock.api.test.annotation;

import com.mvc.json.mock.api.test.MvcJsonMockApiExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(MvcJsonMockApiExtension.class)
public @interface MvcJsonMockApi {

    String jsonPath();
}

package com.mvc.json.mock.api.test.core.executors;

import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import io.vavr.control.Either;
import org.springframework.test.web.servlet.MockMvc;

import static io.vavr.API.Left;

public class PutTestExecutor implements TestExecutor {

    @Override
    public Either<ErrorMessage, EndPointPayload> execute(MockMvc mockMvc, EndPointPayload endPointPayload) {
        return Left(ErrorMessage.builder().message("PUT executor isn't implemented yet").build());
    }
}

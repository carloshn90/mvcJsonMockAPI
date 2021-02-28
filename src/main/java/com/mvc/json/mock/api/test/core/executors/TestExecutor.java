package com.mvc.json.mock.api.test.core.executors;

import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.mvc.json.mock.api.test.payload.ResponsePayload;
import io.vavr.control.Either;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public interface TestExecutor {
    Either<ErrorMessage, EndPointPayload> execute(MockMvc mockMvc, EndPointPayload endPointPayload);

    static void checkResponse(ResultActions resultActions, ResponsePayload response) throws Exception {

        resultActions.andExpect(status().is(response.getStatus()));

        if (response.getBody() != null && !response.getBody().isBlank()) {
            resultActions.andExpect(content().string(response.getBody()));
        } else {
            resultActions.andExpect(jsonPath("$").doesNotExist());
        }
    }
}

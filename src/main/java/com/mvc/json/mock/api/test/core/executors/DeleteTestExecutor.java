package com.mvc.json.mock.api.test.core.executors;

import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.mvc.json.mock.api.test.payload.RequestPayload;
import com.mvc.json.mock.api.test.payload.ResponsePayload;
import io.vavr.control.Either;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static io.vavr.API.Right;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeleteTestExecutor implements TestExecutor {

    @Override
    public Either<ErrorMessage, EndPointPayload> execute(MockMvc mockMvc, EndPointPayload endPointPayload) {
        RequestPayload request = endPointPayload.getRequest();
        ResponsePayload response = endPointPayload.getResponse();

        try {
            checkResponse(mockMvc.perform(createDelete(request)), response);
        } catch (Throwable e) {
            return ErrorMessage.createLeft(e);
        }

        return Right(endPointPayload);
    }

    private MockHttpServletRequestBuilder createDelete(RequestPayload request) {

        MockHttpServletRequestBuilder req = delete(request.getPath())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        request.getMultiValueMap().ifPresent(req::params);

        if (request.getBody() != null && !request.getBody().isBlank()) req.content(request.getBody());

        return req;
    }

    private void checkResponse(ResultActions resultActions, ResponsePayload response) throws Exception {

        if (response.getBody() != null && !response.getBody().isBlank()) {
            resultActions.andExpect(content().string(response.getBody()));
        } else {
            resultActions.andExpect(jsonPath("$").doesNotExist());
        }

        resultActions.andExpect(status().is(response.getStatus()));
    }
}

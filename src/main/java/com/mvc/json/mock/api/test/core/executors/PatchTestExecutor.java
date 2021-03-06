package com.mvc.json.mock.api.test.core.executors;

import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.mvc.json.mock.api.test.payload.RequestPayload;
import com.mvc.json.mock.api.test.payload.ResponsePayload;
import io.vavr.control.Either;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class PatchTestExecutor implements TestExecutor {

    @Override
    public Either<ErrorMessage, EndPointPayload> execute(MockMvc mockMvc, EndPointPayload endPointPayload) {
        RequestPayload request = endPointPayload.getRequest();
        ResponsePayload response = endPointPayload.getResponse();

        try {
            TestExecutor.checkResponse(mockMvc.perform(createPatch(request)), response);
        } catch (Throwable e) {
            return ErrorMessage.createLeft(e);
        }

        return Right(endPointPayload);
    }

    private MockHttpServletRequestBuilder createPatch(RequestPayload request) {

        MockHttpServletRequestBuilder req = patch(request.getPath())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        request.getMultiValueMap().ifPresent(req::params);

        if (request.getBody() != null && !request.getBody().isBlank()) req.content(request.getBody());

        return req;
    }
}

package com.mvc.json.mock.api.test.core.executors;

import com.mvc.json.mock.api.test.controller.GetController;
import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.mvc.json.mock.api.test.payload.RequestPayload;
import com.mvc.json.mock.api.test.payload.ResponsePayload;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTestExecutorTest {

    @InjectMocks
    private GetController controller;

    private MockMvc mockMvc;

    private GetTestExecutor getTestExecutor;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
        this.getTestExecutor = new GetTestExecutor();
    }

    @Test
    void getController_EndPointDoesntExistExpectedStatus200_ErrorMessage() {

        EndPointPayload endPointPayloadMock = mock(EndPointPayload.class);
        RequestPayload requestPayloadMock = mock(RequestPayload.class);
        ResponsePayload responsePayloadMock = mock(ResponsePayload.class);

        when(requestPayloadMock.getPath()).thenReturn("/");

        when(responsePayloadMock.getStatus()).thenReturn(200);

        when(endPointPayloadMock.getRequest()).thenReturn(requestPayloadMock);
        when(endPointPayloadMock.getResponse()).thenReturn(responsePayloadMock);

        Either<ErrorMessage, EndPointPayload> response = this.getTestExecutor.execute(this.mockMvc, endPointPayloadMock);

        assertTrue(response.isLeft());
        assertNotNull(response.getLeft());
        assertEquals("Response status expected:<200> but was:<404>", response.getLeft().getMessage());
    }

    @Test
    void getController_SimpleEndPoint_ExpectedOkStatus() {

        EndPointPayload endPointPayloadMock = mock(EndPointPayload.class);
        RequestPayload requestPayloadMock = mock(RequestPayload.class);
        ResponsePayload responsePayloadMock = mock(ResponsePayload.class);

        when(requestPayloadMock.getPath()).thenReturn("/" + GetController.SIMPLE_END_POINT);

        when(responsePayloadMock.getBody()).thenReturn(GetController.SIMPLE_END_POINT);
        when(responsePayloadMock.getStatus()).thenReturn(200);

        when(endPointPayloadMock.getRequest()).thenReturn(requestPayloadMock);
        when(endPointPayloadMock.getResponse()).thenReturn(responsePayloadMock);

        Either<ErrorMessage, EndPointPayload> response = this.getTestExecutor.execute(this.mockMvc, endPointPayloadMock);

        assertTrue(response.isRight());
    }

    @Test
    void getController_QueryParametersEndPoint_ExpectedOkStatus() {

        EndPointPayload endPointPayloadMock = mock(EndPointPayload.class);
        RequestPayload requestPayloadMock = mock(RequestPayload.class);
        ResponsePayload responsePayloadMock = mock(ResponsePayload.class);
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();

        paramMap.add("param", "message");
        paramMap.add("number", "39128");

        when(requestPayloadMock.getPath()).thenReturn("/" + GetController.QUERY_PARAMETER_END_POINT);
        when(requestPayloadMock.getMultiValueMap()).thenReturn(Optional.of(paramMap));

        when(responsePayloadMock.getBody()).thenReturn(GetController.QUERY_PARAMETER_END_POINT + ", message, 39128");
        when(responsePayloadMock.getStatus()).thenReturn(200);

        when(endPointPayloadMock.getRequest()).thenReturn(requestPayloadMock);
        when(endPointPayloadMock.getResponse()).thenReturn(responsePayloadMock);

        Either<ErrorMessage, EndPointPayload> response = this.getTestExecutor.execute(this.mockMvc, endPointPayloadMock);

        assertTrue(response.isRight());
    }

    @Test
    void getController_EmptyEndPoint_ExpectedOkStatusBodyEmpty() {

        EndPointPayload endPointPayloadMock = mock(EndPointPayload.class);
        RequestPayload requestPayloadMock = mock(RequestPayload.class);
        ResponsePayload responsePayloadMock = mock(ResponsePayload.class);

        when(requestPayloadMock.getPath()).thenReturn("/" + GetController.EMPTY_END_POINT);

        when(responsePayloadMock.getStatus()).thenReturn(200);

        when(endPointPayloadMock.getRequest()).thenReturn(requestPayloadMock);
        when(endPointPayloadMock.getResponse()).thenReturn(responsePayloadMock);

        Either<ErrorMessage, EndPointPayload> response = this.getTestExecutor.execute(this.mockMvc, endPointPayloadMock);

        assertTrue(response.isRight());
    }
}

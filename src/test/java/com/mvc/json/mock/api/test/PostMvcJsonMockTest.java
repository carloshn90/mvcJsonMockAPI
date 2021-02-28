package com.mvc.json.mock.api.test;

import com.mvc.json.mock.api.test.annotation.MvcJsonMockApi;
import com.mvc.json.mock.api.test.annotation.TestEndPoint;
import com.mvc.json.mock.api.test.controller.PostController;
import com.mvc.json.mock.api.test.controller.ServiceMock;
import com.mvc.json.mock.api.test.core.MvcJsonMock;
import com.mvc.json.mock.api.test.exception.ApiException;
import com.mvc.json.mock.api.test.exception.ApiRunException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@MvcJsonMockApi(jsonPath = "post-tests.json")
class PostMvcJsonMockTest {

    private ServiceMock serviceMock;
    private MvcJsonMock mvcJsonMock;

    @BeforeEach
    void setUp() {
        this.serviceMock = mock(ServiceMock.class);
        PostController controller = new PostController(this.serviceMock);

        this.mvcJsonMock = MvcJsonMockBuilder
                .standaloneSetup(controller)
                .build();
    }

    @TestEndPoint(name = "expected-200-actual-500-status")
    void expected200Actual500StatusPost_ResponseStatusError() {
        ApiRunException exception = Assertions.assertThrows(ApiRunException.class,
                () -> this.mvcJsonMock.callEndPoint()
        );

        assertTrue(exception.getMessage().contains("Response status expected:<200> but was:<500>"));
    }

    @TestEndPoint(name = "complex-end-point-with-mock-services")
    void endPointWithMockService_ResponseOkStatusWithBody() throws ApiException {

        String paramValue = "post-test";
        String bodyValue = "body message";

        when(this.serviceMock.postResponse(paramValue, bodyValue)).thenReturn("mock post response");

        this.mvcJsonMock.callEndPoint();

        verify(this.serviceMock, timeout(1)).postResponse(paramValue, bodyValue);
    }
}

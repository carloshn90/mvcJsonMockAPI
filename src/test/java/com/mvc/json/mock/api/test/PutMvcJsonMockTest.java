package com.mvc.json.mock.api.test;

import com.mvc.json.mock.api.test.annotation.MvcJsonMockApi;
import com.mvc.json.mock.api.test.annotation.TestEndPoint;
import com.mvc.json.mock.api.test.controller.DeleteController;
import com.mvc.json.mock.api.test.controller.PutController;
import com.mvc.json.mock.api.test.controller.ServiceMock;
import com.mvc.json.mock.api.test.core.MvcJsonMock;
import com.mvc.json.mock.api.test.exception.ApiException;
import com.mvc.json.mock.api.test.exception.ApiRunException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@MvcJsonMockApi(jsonPath = "put-tests.json")
class PutMvcJsonMockTest {

    private ServiceMock serviceMock;
    private MvcJsonMock mvcJsonMock;

    @BeforeEach
    void setUp() {
        this.serviceMock = mock(ServiceMock.class);
        PutController controller = new PutController(this.serviceMock);

        this.mvcJsonMock = MvcJsonMockBuilder
                .standaloneSetup(controller)
                .build();
    }

    @TestEndPoint(name = "expected-200-actual-500-status")
    void expected200Actual500StatusPut_ResponseStatusError() {
        ApiRunException exception = Assertions.assertThrows(ApiRunException.class,
                () -> this.mvcJsonMock.testEndPoint()
        );

        assertTrue(exception.getMessage().contains("Response status expected:<200> but was:<500>"));
    }

    @TestEndPoint(name = "complex-end-point-with-mock-services")
    void endPointWithMockService_ResponseOkStatusWithBody() throws ApiException {

        String paramValue = "put-test";
        String bodyValue = "body message";

        when(this.serviceMock.putResponse(paramValue, bodyValue)).thenReturn("mock put response");

        this.mvcJsonMock.testEndPoint();

        verify(this.serviceMock, timeout(1)).putResponse(paramValue, bodyValue);
    }
}

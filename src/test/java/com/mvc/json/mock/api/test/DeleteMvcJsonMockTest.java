package com.mvc.json.mock.api.test;

import com.mvc.json.mock.api.test.annotation.MvcJsonMockApi;
import com.mvc.json.mock.api.test.annotation.TestEndPoint;
import com.mvc.json.mock.api.test.controller.DeleteController;
import com.mvc.json.mock.api.test.controller.GetController;
import com.mvc.json.mock.api.test.controller.ServiceMock;
import com.mvc.json.mock.api.test.core.MvcJsonMock;
import com.mvc.json.mock.api.test.exception.ApiException;
import com.mvc.json.mock.api.test.exception.ApiRunException;
import com.mvc.json.mock.api.test.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@MvcJsonMockApi(jsonPath = "delete-tests.json")
class DeleteMvcJsonMockTest {

    private ServiceMock serviceMock;
    private MvcJsonMock mvcJsonMock;

    @BeforeEach
    void setUp() {
        this.serviceMock = mock(ServiceMock.class);
        DeleteController controller = new DeleteController(this.serviceMock);

        this.mvcJsonMock = MvcJsonMockBuilder
                .standaloneSetup(controller)
                .build();
    }

    @TestEndPoint(name = "expected-200-actual-500-status")
    void expected200Actual500StatusDelete_ResponseStatusError() {
        ApiRunException exception = Assertions.assertThrows(ApiRunException.class,
                () -> this.mvcJsonMock.run()
        );

        assertTrue(exception.getMessage().contains("Response status expected:<200> but was:<500>"));
    }

    @TestEndPoint(name = "complex-end-point-with-mock-services")
    void endPointWithMockService_ResponseOkStatusWithBody() throws ApiException {

        String paramValue = "delete-test";
        String bodyValue = "body message";

        when(this.serviceMock.deleteResponse(paramValue, bodyValue)).thenReturn("mock delete response");

        this.mvcJsonMock.run();

        verify(this.serviceMock, timeout(1)).deleteResponse(paramValue, bodyValue);
    }
}

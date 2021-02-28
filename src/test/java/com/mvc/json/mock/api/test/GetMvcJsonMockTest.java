package com.mvc.json.mock.api.test;

import com.mvc.json.mock.api.test.annotation.MvcJsonMockApi;
import com.mvc.json.mock.api.test.annotation.TestEndPoint;
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

@MvcJsonMockApi(jsonPath = "get-tests.json")
class GetMvcJsonMockTest {

    private ServiceMock serviceMock;
    private MvcJsonMock mvcJsonMock;

    @BeforeEach
    void setUp() {
        this.serviceMock = mock(ServiceMock.class);
        GetController controller = new GetController(this.serviceMock);

        this.mvcJsonMock = MvcJsonMockBuilder
                .standaloneSetup(controller)
                .build();
    }

    @TestEndPoint(name = "missing-mandatory-fields")
    void missingMandatoryFieldsJson_ValidationError() {
        ValidationException validationException = Assertions.assertThrows(ValidationException.class,
                () -> this.mvcJsonMock.callEndPoint()
        );

        assertTrue(validationException.getMessage().contains("End-Point json validation errors: \n"));
        assertTrue(validationException.getMessage().contains("             * End-point request definition is mandatory\n"));
        assertTrue(validationException.getMessage().contains("             * End-point response definition is mandatory\n"));
    }

    @TestEndPoint(name = "expected-400-actual-200-status")
    void expected400Actual200Status_ResponseStatusError() {
        ApiRunException exception = Assertions.assertThrows(ApiRunException.class,
                () -> this.mvcJsonMock.callEndPoint()
        );

        assertTrue(exception.getMessage().contains("Response status expected:<400> but was:<200>"));
    }

    @TestEndPoint(name = "query-param-response-200-status")
    void queryParamGetRequest_ResponseOkStatusWithBody() throws ApiException {
        this.mvcJsonMock.callEndPoint();
    }

    @TestEndPoint(name = "end-point-with-mock-services")
    void endPointWithMockService_ResponseOkStatusWithBody() throws ApiException {

        when(this.serviceMock.getResponse()).thenReturn("mock service response");

        this.mvcJsonMock.callEndPoint();

        verify(this.serviceMock, timeout(1)).getResponse();
    }
}

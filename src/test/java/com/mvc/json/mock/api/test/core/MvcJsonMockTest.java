package com.mvc.json.mock.api.test.core;

import com.mvc.json.mock.api.test.enums.MethodEnum;
import com.mvc.json.mock.api.test.file.FileReader;
import com.mvc.json.mock.api.test.json.JsonConverter;
import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.mvc.json.mock.api.test.payload.RequestPayload;
import com.mvc.json.mock.api.test.payload.ResponsePayload;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MvcJsonMockTest {

    private MockMvc mockMvc;

    private FileReader fileReaderMock;

    private JsonConverter jsonConverterMock;

    private MvcJsonMock mvcJsonMock;

    @BeforeEach
    void setUp() {
        this.fileReaderMock = mock(FileReader.class);
        this.jsonConverterMock = mock(JsonConverter.class);

        this.mvcJsonMock = MvcJsonMock.builder()
                .mockMvc(this.mockMvc)
                .fileReader(this.fileReaderMock)
                .jsonConverter(this.jsonConverterMock)
                .build();
    }

    @Test
    void getEndPointPayloadToTest_EndPointNameNul_ReturnErrorMessage() {

        Either<ErrorMessage, MvcJsonMock> errorMessage = this.mvcJsonMock.getMvcJsonTestWithEndPointPayload(null);

        assertTrue(errorMessage.isLeft());
        assertNotNull(errorMessage.getLeft());
        assertEquals("Error getting the end-point because the name is null", errorMessage.getLeft().getMessage());
    }

    @Test
    void getEndPointPayloadToTest_EndPointNameNoExist_ReturnErrorMessage() {

        String endPointNameNoExist = "no exist";
        String jsonStr = "{}";
        EndPointPayload endPointPayloadMock = mock(EndPointPayload.class);
        Set<EndPointPayload> endPointPayloadMockSet = Collections.singleton(endPointPayloadMock);

        when(this.fileReaderMock.readJsonFromFile()).thenReturn(Either.right(jsonStr));
        when(this.jsonConverterMock.convertJsonToEndPointPayload(jsonStr)).thenReturn(Either.right(endPointPayloadMockSet));

        Either<ErrorMessage, MvcJsonMock> errorMessage = this.mvcJsonMock.getMvcJsonTestWithEndPointPayload(endPointNameNoExist);

        assertTrue(errorMessage.isLeft());
        assertNotNull(errorMessage.getLeft());
        assertEquals("Missing endPoint no exist in the json file", errorMessage.getLeft().getMessage());
    }

    @Test
    void getEndPointPayloadToTest_EndPointNameExist_ReturnEndPoint() {

        String endPointNameExist = "exist";
        String jsonStr = "{}";
        EndPointPayload endPointPayloadMock = mock(EndPointPayload.class);
        Set<EndPointPayload> endPointPayloadMockSet = Collections.singleton(endPointPayloadMock);

        when(this.fileReaderMock.readJsonFromFile()).thenReturn(Either.right(jsonStr));
        when(this.jsonConverterMock.convertJsonToEndPointPayload(jsonStr)).thenReturn(Either.right(endPointPayloadMockSet));
        when(endPointPayloadMock.getName()).thenReturn(endPointNameExist);

        Either<ErrorMessage, MvcJsonMock> endPointPayload = this.mvcJsonMock.getMvcJsonTestWithEndPointPayload(endPointNameExist);

        assertTrue(endPointPayload.isRight());
        assertNotNull(endPointPayload.get().getEndPointPayload());
        assertEquals(endPointPayloadMock, endPointPayload.get().getEndPointPayload());
    }

    @Test
    void validateEndPoint_EndPointNull_ErrorMessage() {

        MvcJsonMock mvcJsonMock = MvcJsonMock.builder().build();

        Either<Set<ErrorMessage>, MvcJsonMock> eitherErrorMessageSet = mvcJsonMock.validateEndPoint();

        assertTrue(eitherErrorMessageSet.isLeft());

        Set<String> errorMessageSet = eitherErrorMessageSet.getLeft().stream()
                .map(ErrorMessage::getMessage)
                .collect(Collectors.toSet());

        assertThat(errorMessageSet, hasSize(1));
        assertTrue(errorMessageSet.contains("End-point payload null"));
    }

    @Test
    void validateEndPoint_NameRequestAndResponseNull_ErrorMessage() {

        EndPointPayload endPointPayload = EndPointPayload.builder().build();
        MvcJsonMock mvcJsonMock = MvcJsonMock.builder()
                .endPointPayload(endPointPayload)
                .build();

        Either<Set<ErrorMessage>, MvcJsonMock> eitherErrorMessageSet = mvcJsonMock.validateEndPoint();

        assertTrue(eitherErrorMessageSet.isLeft());

        Set<String> errorMessageSet = eitherErrorMessageSet.getLeft().stream()
                .map(ErrorMessage::getMessage)
                .collect(Collectors.toSet());

        assertThat(errorMessageSet, hasSize(3));
        assertTrue(errorMessageSet.contains("End-point name is mandatory"));
        assertTrue(errorMessageSet.contains("End-point request definition is mandatory"));
        assertTrue(errorMessageSet.contains("End-point response definition is mandatory"));
    }

    @Test
    void validateEndPoint_MethodPathAndStatusNull_ErrorMessage() {

        EndPointPayload endPointPayload = EndPointPayload.builder()
                .name("name")
                .request(RequestPayload.builder().build())
                .response(ResponsePayload.builder().build())
                .build();
        MvcJsonMock mvcJsonMock = MvcJsonMock.builder()
                .endPointPayload(endPointPayload)
                .build();

        Either<Set<ErrorMessage>, MvcJsonMock> eitherErrorMessageSet = mvcJsonMock.validateEndPoint();

        assertTrue(eitherErrorMessageSet.isLeft());

        Set<String> errorMessageSet = eitherErrorMessageSet.getLeft().stream()
                .map(ErrorMessage::getMessage)
                .collect(Collectors.toSet());

        assertThat(errorMessageSet, hasSize(3));
        assertTrue(errorMessageSet.contains("Request method type is mandatory"));
        assertTrue(errorMessageSet.contains("Request path is mandatory"));
        assertTrue(errorMessageSet.contains("Response status is mandatory"));
    }

    @Test
    void validateEndPoint_NoValidationErrors_Right() {

        RequestPayload requestPayload = RequestPayload.builder()
                .path("path")
                .method(MethodEnum.GET)
                .build();
        ResponsePayload responsePayload = ResponsePayload.builder()
                .status(200)
                .build();
        EndPointPayload endPointPayload = EndPointPayload.builder()
                .name("name")
                .request(requestPayload)
                .response(responsePayload)
                .build();
        MvcJsonMock mvcJsonMock = MvcJsonMock.builder()
                .endPointPayload(endPointPayload)
                .build();

        Either<Set<ErrorMessage>, MvcJsonMock> eitherErrorMessageSet = mvcJsonMock.validateEndPoint();

        assertTrue(eitherErrorMessageSet.isRight());
    }

}

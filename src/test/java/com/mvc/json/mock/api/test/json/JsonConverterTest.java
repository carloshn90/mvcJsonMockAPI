package com.mvc.json.mock.api.test.json;

import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonConverterTest {

    private ObjectMapper objectMapperMock;

    private JsonConverter jsonConverter;

    @BeforeEach
    void setUp() {
       this.objectMapperMock = mock(ObjectMapper.class);
       this.jsonConverter = new JsonConverter(this.objectMapperMock);
    }

    @Test
    void convertJsonToEndPointPayload_NullJson_ReturnError() {

        Either<ErrorMessage, Set<EndPointPayload>> error = this.jsonConverter.convertJsonToEndPointPayload(null);

        assertTrue(error.isLeft());
        assertNotNull(error.getLeft());
        assertEquals("Json to convert is null", error.getLeft().getMessage());
    }

    @Test
    void convertJsonToEndPointPayload_ReadValueException_ReturnError() throws JsonProcessingException {

        String json = "{}";

        when(this.objectMapperMock.readValue(json, JsonConverter.typeReference)).thenThrow(new JsonProcessingException("empty"){});

        Either<ErrorMessage, Set<EndPointPayload>> error = this.jsonConverter.convertJsonToEndPointPayload(json);

        assertTrue(error.isLeft());
        assertNotNull(error.getLeft());
        assertEquals("Error converting json to payload: empty", error.getLeft().getMessage());
    }

    @Test
    void convertJsonToEndPointPayload_ReadValueCorrect_ReturnEndPointPayload() throws JsonProcessingException {

        String json = "{}";
        EndPointPayload endPointPayloadMock = mock(EndPointPayload.class);
        Set<EndPointPayload> endPointPayloadMockSet = Collections.singleton(endPointPayloadMock);

        when(this.objectMapperMock.readValue(json, JsonConverter.typeReference)).thenReturn(endPointPayloadMockSet);

        Either<ErrorMessage, Set<EndPointPayload>> endPointPayloadSetResult = this.jsonConverter.convertJsonToEndPointPayload(json);

        assertTrue(endPointPayloadSetResult.isRight());
        assertEquals(endPointPayloadMockSet, endPointPayloadSetResult.get());
    }
}

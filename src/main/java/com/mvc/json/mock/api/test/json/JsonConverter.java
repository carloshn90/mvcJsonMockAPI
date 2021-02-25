package com.mvc.json.mock.api.test.json;

import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvc.json.mock.api.test.enums.ErrorTypeEnum;
import io.vavr.control.Either;

import java.util.Set;

import static io.vavr.API.Right;

public class JsonConverter {

    public static final TypeReference<Set<EndPointPayload>> typeReference = new TypeReference<>(){};

    private final ObjectMapper objectMapper;

    public JsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Either<ErrorMessage, Set<EndPointPayload>> convertJsonToEndPointPayload(String json) {

        if (json == null) return ErrorMessage.createLeft("Json to convert is null", ErrorTypeEnum.VALIDATION_ERROR);

        try {
            return Right(this.objectMapper.readValue(json, typeReference));
        } catch (JsonProcessingException e) {
            return ErrorMessage.createLeft(e, "Error converting json to payload: ");
        }
    }
}

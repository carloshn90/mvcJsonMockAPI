package com.mvc.json.mock.api.test.core;

import com.mvc.json.mock.api.test.exception.ApiException;
import com.mvc.json.mock.api.test.file.FileReader;
import com.mvc.json.mock.api.test.json.JsonConverter;
import com.mvc.json.mock.api.test.payload.EndPointPayload;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.mvc.json.mock.api.test.util.ValidationUtil;
import io.vavr.control.Either;
import lombok.Builder;
import lombok.Getter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static com.mvc.json.mock.api.test.enums.ErrorTypeEnum.VALIDATION_ERROR;
import static io.vavr.API.Left;
import static io.vavr.API.Right;

@Getter
@Builder
public class MvcJsonMock {

    private final MockMvc mockMvc;

    private final FileReader fileReader;

    private final JsonConverter jsonConverter;

    private final EndPointPayload endPointPayload;

    public Either<ErrorMessage, MvcJsonMock> getMvcJsonTestWithEndPointPayload(String endPointName) {

        if (endPointName == null) return ErrorMessage.createLeft("Error getting the end-point because the name is null", VALIDATION_ERROR);

        return this.fileReader.readJsonFromFile()
                .flatMap(this.jsonConverter::convertJsonToEndPointPayload)
                .flatMap(endPointPayloadSet -> this.getEndPointByName(endPointPayloadSet, endPointName))
                .map(this::addEndPoint);
    }

    public void testEndPoint() throws ApiException {

        this.validateEndPoint()
                .mapLeft(ValidationUtil::createValidationErrorMessage)
                .flatMap(mvcJsonMock -> this.executeTest(mvcJsonMock.endPointPayload))
                .getOrElseThrow(ErrorMessage::getApiException);
    }

    public Either<Set<ErrorMessage>, MvcJsonMock> validateEndPoint() {

        if (this.endPointPayload == null)
            return Left(Collections.singleton(ErrorMessage.create("End-point payload null", VALIDATION_ERROR)));

        Set<ErrorMessage> errors = this.endPointPayload.validate();

        return errors.isEmpty() ? Right(this) : Left(errors);
    }

    private Either<ErrorMessage, EndPointPayload> executeTest(EndPointPayload endPointPayload) {

        return endPointPayload.getTestExecutor()
                .execute(this.mockMvc, endPointPayload);
    }

    private Either<ErrorMessage, EndPointPayload> getEndPointByName(Set<EndPointPayload> endPointPayloadSet, String endPointName) {

        return endPointPayloadSet.stream()
                .filter(endPoint -> endPointName.equals(endPoint.getName()))
                .findAny()
                .<Either<ErrorMessage, EndPointPayload>>map(Either::right)
                .orElseGet(() -> ErrorMessage.createLeft("Missing endPoint " + endPointName + " in the json file", VALIDATION_ERROR));
    }

    private MvcJsonMock addEndPoint(EndPointPayload endPointPayload) {

        return MvcJsonMock.builder()
                .mockMvc(this.mockMvc)
                .fileReader(this.fileReader)
                .jsonConverter(this.jsonConverter)
                .endPointPayload(endPointPayload)
                .build();
    }
}

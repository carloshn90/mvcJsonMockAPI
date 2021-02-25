package com.mvc.json.mock.api.test;

import com.mvc.json.mock.api.test.annotation.MvcJsonMockApi;
import com.mvc.json.mock.api.test.annotation.TestEndPoint;
import com.mvc.json.mock.api.test.core.MvcJsonMock;
import com.mvc.json.mock.api.test.enums.ErrorTypeEnum;
import com.mvc.json.mock.api.test.file.FileHelperImpl;
import com.mvc.json.mock.api.test.file.FileReader;
import com.mvc.json.mock.api.test.json.JsonConverter;
import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.util.Arrays;

import static io.vavr.API.Right;
import static io.vavr.API.Tuple;

public class MvcJsonMockApiExtension implements BeforeTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        TestEndPoint testEndPoint = extensionContext.getRequiredTestMethod().getAnnotation(TestEndPoint.class);
        MvcJsonMockApi mvcJsonMockApi = extensionContext.getRequiredTestClass().getAnnotation(MvcJsonMockApi.class);
        String endPointName = testEndPoint.name();

        this.getMvcJsonTest(extensionContext)
                .map(mvcJsonTest -> this.initMvcJsonTest(mvcJsonTest, mvcJsonMockApi.jsonPath()))
                .flatMap(mvcJsonTest -> mvcJsonTest.getMvcJsonTestWithEndPointPayload(endPointName))
                .flatMap(mvcJsonTest -> this.setMvcJsonTest(extensionContext, mvcJsonTest))
                .getOrElseThrow(ErrorMessage::getApiException);
    }

    private Either<ErrorMessage, MvcJsonMock> getMvcJsonTest(ExtensionContext extensionContext) {

        return getMvcJsonTestField(extensionContext)
                .flatMap(field -> this.getInstance(extensionContext, field))
                .flatMap(fieldInstanceTuple -> this.getMvcJsonTest(fieldInstanceTuple._1, fieldInstanceTuple._2));
    }

    private Either<ErrorMessage, Field> getMvcJsonTestField(ExtensionContext extensionContext) {
        Field[] fieldArray = extensionContext.getRequiredTestClass().getDeclaredFields();

        return Arrays.stream(fieldArray)
                .filter(field -> MvcJsonMock.class.isAssignableFrom(field.getType()))
                .findAny()
                .<Either<ErrorMessage, Field>>map(Either::right)
                .orElseGet(() -> ErrorMessage.createLeft("No MvcJsonTest field found it", ErrorTypeEnum.EXECUTION_ERROR));
    }

    private Either<ErrorMessage, MvcJsonMock> getMvcJsonTest(Field field, Object instance) {
        try {
            field.setAccessible(true);
            return Right((MvcJsonMock) field.get(instance));
        } catch (IllegalAccessException e) {
            return ErrorMessage.createLeft("No MvcJsonTest field found it", ErrorTypeEnum.EXECUTION_ERROR);
        }
    }

    private Either<ErrorMessage, MvcJsonMock> setMvcJsonTest(ExtensionContext extensionContext, MvcJsonMock mvcJsonMock) {

        return getMvcJsonTestField(extensionContext)
                .flatMap(field -> this.getInstance(extensionContext, field))
                .flatMap(fieldInstanceTuple -> this.setMvcJsonTestToField(fieldInstanceTuple._1, fieldInstanceTuple._2, mvcJsonMock));
    }

    private Either<ErrorMessage, MvcJsonMock> setMvcJsonTestToField(Field field, Object instance, MvcJsonMock mvcJsonMock) {
        try {
            field.setAccessible(true);
            field.set(instance, mvcJsonMock);
            return Right(mvcJsonMock);
        } catch (IllegalAccessException e) {
            return ErrorMessage.createLeft("", ErrorTypeEnum.VALIDATION_ERROR);
        }
    }

    private MvcJsonMock initMvcJsonTest(MvcJsonMock mvcJsonMock, String jsonPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader fileReader = new FileReader(new FileHelperImpl(), FileReader.getJsonPath(jsonPath));
        JsonConverter jsonConverter = new JsonConverter(objectMapper);

        return MvcJsonMock.builder()
                .mockMvc(mvcJsonMock.getMockMvc())
                .fileReader(fileReader)
                .jsonConverter(jsonConverter)
                .build();
    }

    private Either<ErrorMessage, Tuple2<Field, Object>> getInstance(ExtensionContext extensionContext, Field field) {

        return extensionContext.getTestInstance()
                .<Either<ErrorMessage, Tuple2<Field, Object>>>map(instance -> Right(Tuple(field, instance)))
                .orElseGet(() -> ErrorMessage.createLeft("Test instance doesn't found", ErrorTypeEnum.VALIDATION_ERROR));
    }
}

package com.mvc.json.mock.api.test.payload;

import com.mvc.json.mock.api.test.enums.ErrorTypeEnum;
import com.mvc.json.mock.api.test.exception.ApiException;
import com.mvc.json.mock.api.test.exception.ApiRunException;
import com.mvc.json.mock.api.test.exception.ValidationException;
import io.vavr.control.Either;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

import static io.vavr.API.Left;

@Builder
@Getter
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = -8931656721989521181L;

    private final String message;

    private final StackTraceElement[] stackTrace;

    private final ErrorTypeEnum errorTypeEnum;

    public ApiException getApiException() {

        return this.errorTypeEnum == ErrorTypeEnum.VALIDATION_ERROR ? this.getValidationException() : this.getApiRunException();
    }

    public static ErrorMessage create(String message, ErrorTypeEnum errorTypeEnum) {

        return ErrorMessage.builder()
                .message(message)
                .errorTypeEnum(errorTypeEnum)
                .build();
    }

    public static ErrorMessage create(Throwable e) {

        return ErrorMessage.builder()
                .message(e.getMessage())
                .stackTrace(e.getStackTrace())
                .build();
    }

    public static ErrorMessage create(Exception e, String prefixMessage) {

        return ErrorMessage.builder()
                .message(prefixMessage +  e.getMessage())
                .stackTrace(e.getStackTrace())
                .build();
    }

    public static <T> Either<ErrorMessage, T> createLeft(String message, ErrorTypeEnum errorTypeEnum) {

        return Left(create(message, errorTypeEnum));
    }

    public static <T> Either<ErrorMessage, T> createLeft(Throwable e) {

        return Left(create(e));
    }

    public static <T> Either<ErrorMessage, T> createLeft(Exception e, String prefixMessage) {

        return Left(create(e, prefixMessage));
    }

    private ValidationException getValidationException() {
        ValidationException ex = new ValidationException(this.message);
        if (this.stackTrace != null) ex.setStackTrace(this.stackTrace);

        return ex;
    }

    private ApiRunException getApiRunException() {
        return new ApiRunException(this.message, this.stackTrace);
    }
}

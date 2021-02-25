package com.mvc.json.mock.api.test.util;

import com.mvc.json.mock.api.test.payload.ErrorMessage;
import com.mvc.json.mock.api.test.enums.ErrorTypeEnum;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ValidationUtil {

    private ValidationUtil() {}

    public static boolean isBlank(String str) {
       return isEmpty(str) || str.isBlank();
    }

    public static boolean isEmpty(String str) {
        return Objects.isNull(str) || str.isEmpty();
    }

    public static ErrorMessage createValidationErrorMessage(Set<ErrorMessage> errorMessageSet) {

        String errorMsg = "End-Point json validation errors: \n";
        errorMsg += errorMessageSet.stream()
                .map(ValidationUtil::createValidationErrorLine)
                .collect(Collectors.joining());

        return ErrorMessage.create(errorMsg, ErrorTypeEnum.VALIDATION_ERROR);
    }

    private static String createValidationErrorLine(ErrorMessage errorMessage) {
        return "             * " + errorMessage.getMessage() + "\n";
    }
}

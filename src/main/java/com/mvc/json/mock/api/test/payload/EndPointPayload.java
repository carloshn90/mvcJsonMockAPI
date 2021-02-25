package com.mvc.json.mock.api.test.payload;

import com.mvc.json.mock.api.test.core.executors.TestExecutor;
import com.mvc.json.mock.api.test.enums.ErrorTypeEnum;
import com.mvc.json.mock.api.test.util.ValidationUtil;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Builder
@Getter
public class EndPointPayload implements Serializable {

    private static final long serialVersionUID = -5202016105410921026L;

    private final String name;

    private final RequestPayload request;

    private final ResponsePayload response;

    public TestExecutor getTestExecutor() {
        return this.getRequest().getTestExecutor();
    }

    public Set<ErrorMessage> validate() {
        Set<ErrorMessage> validationErrors = new HashSet<>();

        if (ValidationUtil.isBlank(this.name)) validationErrors.add(ErrorMessage.create("End-point name is mandatory", ErrorTypeEnum.VALIDATION_ERROR));

        if (isNull(this.request)) validationErrors.add(ErrorMessage.create("End-point request definition is mandatory", ErrorTypeEnum.VALIDATION_ERROR));
        else validationErrors.addAll(request.validate());

        if (isNull(this.response)) validationErrors.add(ErrorMessage.create("End-point response definition is mandatory", ErrorTypeEnum.VALIDATION_ERROR));
        else validationErrors.addAll(response.validate());

        return validationErrors;
    }
}

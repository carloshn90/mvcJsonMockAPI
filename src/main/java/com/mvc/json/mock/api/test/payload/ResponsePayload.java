package com.mvc.json.mock.api.test.payload;

import com.mvc.json.mock.api.test.enums.ErrorTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;


@Builder
@Getter
public class ResponsePayload implements Serializable {

    private static final long serialVersionUID = 1476827844884872773L;

    private final Integer status;

    private final String body;

    public Set<ErrorMessage> validate() {
        Set<ErrorMessage> validationErrorSet = new HashSet<>();

        if (isNull(this.status)) validationErrorSet.add(ErrorMessage.create("Response status is mandatory", ErrorTypeEnum.VALIDATION_ERROR));

        return validationErrorSet;
    }
}

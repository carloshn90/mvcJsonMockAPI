package com.mvc.json.mock.api.test.payload;

import com.mvc.json.mock.api.test.core.executors.TestExecutor;
import com.mvc.json.mock.api.test.enums.MethodEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvc.json.mock.api.test.enums.ErrorTypeEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@Builder
@Getter
public class RequestPayload implements Serializable {

    private static final long serialVersionUID = -1656176249705081950L;

    private final MethodEnum method;

    private final String path;

    private final String body;

    @JsonProperty("queryParameters")
    private final Set<QueryParameterPayload> queryParameterPayloadSet;

    public TestExecutor getTestExecutor() {
        return method.getExecutor();
    }

    public Optional<MultiValueMap<String, String>> getMultiValueMap() {

        if (this.queryParameterPayloadSet == null) return Optional.empty();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        for (QueryParameterPayload queryParam : this.getQueryParameterPayloadSet()) {
            param.add(queryParam.getName(), queryParam.getValue());
        }

        return Optional.of(param);
    }

    public Set<ErrorMessage> validate() {
        Set<ErrorMessage> validationErrorSet = new HashSet<>();

        if (isNull(this.method)) validationErrorSet.add(ErrorMessage.create("Request method type is mandatory", ErrorTypeEnum.VALIDATION_ERROR));
        if (isNull(this.path)) validationErrorSet.add(ErrorMessage.create("Request path is mandatory", ErrorTypeEnum.VALIDATION_ERROR));

        return validationErrorSet;
    }
}

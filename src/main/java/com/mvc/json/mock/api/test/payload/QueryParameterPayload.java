package com.mvc.json.mock.api.test.payload;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class QueryParameterPayload implements Serializable {

    private static final long serialVersionUID = 9081253755093737088L;

    private final String name;

    private final String value;
}

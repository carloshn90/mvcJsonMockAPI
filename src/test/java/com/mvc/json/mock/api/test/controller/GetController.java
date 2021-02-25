package com.mvc.json.mock.api.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GetController {

    private final ServiceMock serviceMock;

    public static final String EMPTY_END_POINT = "empty-end-point";
    public static final String SIMPLE_END_POINT = "simple-end-point";
    public static final String SERVICE_RESPONSE_MOCK = "service-response-mock";
    public static final String QUERY_PARAMETER_END_POINT = "query-parameter-end-point";

    @GetMapping("/" + EMPTY_END_POINT)
    public ResponseEntity<String> emptyEndPoint() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/" + SIMPLE_END_POINT)
    public ResponseEntity<String> simpleEndPoint() {
        return ResponseEntity.ok(SIMPLE_END_POINT);
    }

    @GetMapping("/" + QUERY_PARAMETER_END_POINT)
    public ResponseEntity<String> queryParameterEndPoint(@RequestParam String param, @RequestParam Long number) {
        return ResponseEntity.ok(QUERY_PARAMETER_END_POINT + ", " + param + ", " + number);
    }

    @GetMapping("/" + SERVICE_RESPONSE_MOCK)
    public ResponseEntity<String> serviceResponseMock() {
        return ResponseEntity.ok(this.serviceMock.getResponse());
    }
}

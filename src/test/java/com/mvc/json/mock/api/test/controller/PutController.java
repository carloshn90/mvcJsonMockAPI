package com.mvc.json.mock.api.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PutController {

    public static final String EMPTY_PUT_END_POINT = "empty-put-end-point";
    public static final String COMPLEX_PUT_END_POINT = "complex-put-end-point";

    private final ServiceMock serviceMock;

    @PutMapping("/" + EMPTY_PUT_END_POINT)
    public ResponseEntity<String> emptyEndPoint() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/" + COMPLEX_PUT_END_POINT)
    public ResponseEntity<String> serviceResponseMock(@RequestParam String putClass, @RequestBody String body) {
        return ResponseEntity.ok(this.serviceMock.putResponse(putClass, body));
    }
}

package com.mvc.json.mock.api.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PostController {

    public static final String EMPTY_POST_END_POINT = "empty-post-end-point";
    public static final String COMPLEX_POST_END_POINT = "complex-post-end-point";

    private final ServiceMock serviceMock;

    @PostMapping("/" + EMPTY_POST_END_POINT)
    public ResponseEntity<String> emptyEndPoint() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/" + COMPLEX_POST_END_POINT)
    public ResponseEntity<String> serviceResponseMock(@RequestParam String postClass, @RequestBody String body) {
        return ResponseEntity.ok(this.serviceMock.postResponse(postClass, body));
    }
}

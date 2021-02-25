package com.mvc.json.mock.api.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DeleteController {

    public static final String EMPTY_DELETE_END_POINT = "empty-delete-end-point";
    public static final String COMPLEX_DELETE_END_POINT = "complex-delete-end-point";

    private final ServiceMock serviceMock;

    @DeleteMapping("/" + EMPTY_DELETE_END_POINT)
    public ResponseEntity<String> emptyEndPoint() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/" + COMPLEX_DELETE_END_POINT)
    public ResponseEntity<String> serviceResponseMock(@RequestParam String deleteClass, @RequestBody String body) {
        return ResponseEntity.ok(this.serviceMock.deleteResponse(deleteClass, body));
    }
}

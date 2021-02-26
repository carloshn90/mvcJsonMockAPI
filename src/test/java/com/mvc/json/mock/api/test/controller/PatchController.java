package com.mvc.json.mock.api.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PatchController {

    public static final String EMPTY_PATCH_END_POINT = "empty-patch-end-point";
    public static final String COMPLEX_PATCH_END_POINT = "complex-patch-end-point";

    private final ServiceMock serviceMock;

    @PatchMapping("/" + EMPTY_PATCH_END_POINT)
    public ResponseEntity<String> emptyEndPoint() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PatchMapping("/" + COMPLEX_PATCH_END_POINT)
    public ResponseEntity<String> serviceResponseMock(@RequestParam String patchClass, @RequestBody String body) {
        return ResponseEntity.ok(this.serviceMock.patchResponse(patchClass, body));
    }
}

package com.mvc.json.mock.api.test.controller;

public interface ServiceMock {

    String getResponse();

    String deleteResponse(String deleteClass, String body);

    String putResponse(String putClass, String body);

    String patchResponse(String patchClass, String body);
}

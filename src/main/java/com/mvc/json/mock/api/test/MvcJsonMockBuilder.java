package com.mvc.json.mock.api.test;

import com.mvc.json.mock.api.test.core.MvcJsonMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public final class MvcJsonMockBuilder {

    private MvcJsonMock mvcJsonMock;

    public MvcJsonMockBuilder(MvcJsonMock mvcJsonMock) {
        this.mvcJsonMock = mvcJsonMock;
    }

    public static MvcJsonMockBuilder standaloneSetup(Object... controllers) {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();

        MvcJsonMock mvcJsonMock = MvcJsonMock.builder()
                .mockMvc(mockMvc)
                .build();

        return new MvcJsonMockBuilder(mvcJsonMock);
    }

    public MvcJsonMock build() {
        return this.mvcJsonMock;
    }

}

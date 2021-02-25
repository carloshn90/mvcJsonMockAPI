package com.mvc.json.mock.api.test.file;

import com.mvc.json.mock.api.test.payload.ErrorMessage;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileReaderTest {

    private FileHelper fileHelperMock;
    private Path jsonPathMock;

    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        this.fileHelperMock = mock(FileHelper.class);
        this.jsonPathMock = mock(Path.class);

        this.fileReader = new FileReader(this.fileHelperMock, this.jsonPathMock);
    }

    @Test
    void readJsonFromFile_ThrowIOException_ReturnErrorMessage() throws IOException {

        when(this.fileHelperMock.readString(this.jsonPathMock)).thenThrow(new IOException("IO Error"));

        Either<ErrorMessage, String> errorMessage = this.fileReader.readJsonFromFile();

        assertTrue(errorMessage.isLeft());
        assertNotNull(errorMessage.getLeft());
        assertEquals("Error reading the json file: IO Error", errorMessage.getLeft().getMessage());
    }

    @Test
    void readJsonFromFile_ReturnJsonString() throws IOException {

        String json = "{}";

        when(this.fileHelperMock.readString(this.jsonPathMock)).thenReturn(json);

        Either<ErrorMessage, String> errorMessage = this.fileReader.readJsonFromFile();

        assertTrue(errorMessage.isRight());
        assertEquals(json, errorMessage.get());
    }

    @Test
    void getJsonPath_VerifyCallWithJsonPath() {

        String jsonPath = "/json";
        String pathExpected = "src/test/resources/json";

        Path pathResult = FileReader.getJsonPath(jsonPath);

        assertNotNull(pathResult);
        assertNotNull(pathResult.toFile());
        assertEquals(pathExpected, pathResult.toFile().getPath());
    }

}

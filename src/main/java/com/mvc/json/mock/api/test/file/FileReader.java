package com.mvc.json.mock.api.test.file;

import com.mvc.json.mock.api.test.payload.ErrorMessage;
import io.vavr.control.Either;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.vavr.API.Right;

public class FileReader {

    private final FileHelper fileHelper;
    private final Path jsonPath;

    public FileReader(FileHelper fileHelper, Path jsonPath) {
        this.fileHelper = fileHelper;
        this.jsonPath = jsonPath;
    }

    public Either<ErrorMessage, String> readJsonFromFile() {

        try {
            return Right(this.fileHelper.readString(this.jsonPath));
        } catch (IOException e) {
            return ErrorMessage.createLeft(e, "Error reading the json file: ");
        }
    }

    public static Path getJsonPath(String jsonPath) {
        return Paths.get("src", "test", "resources", jsonPath);
    }
}

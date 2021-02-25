package com.mvc.json.mock.api.test.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelperImpl implements FileHelper {

    @Override
    public String readString(Path path) throws IOException {
        return Files.readString(path);
    }
}

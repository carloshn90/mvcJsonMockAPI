package com.mvc.json.mock.api.test.file;

import java.io.IOException;
import java.nio.file.Path;

public interface FileHelper {

   String readString(Path path) throws IOException;
}

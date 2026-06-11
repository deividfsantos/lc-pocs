package com.dsantos.scanner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Scanner {
    List<Path> scan(Path directory) throws IOException;
}


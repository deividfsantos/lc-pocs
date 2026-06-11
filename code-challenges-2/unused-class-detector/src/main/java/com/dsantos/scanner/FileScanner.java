package com.dsantos.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileScanner implements Scanner {

    private final String extension;

    public FileScanner(String extension) {
        this.extension = extension;
    }

    @Override
    public List<Path> scan(Path directory) throws IOException {
        try (var stream = Files.walk(directory)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(extension))
                    .collect(Collectors.toList());
        }
    }
}


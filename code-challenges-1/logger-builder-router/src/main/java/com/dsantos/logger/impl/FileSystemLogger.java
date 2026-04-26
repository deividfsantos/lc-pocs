package com.dsantos.logger.impl;

import com.dsantos.logger.LogMessage;
import com.dsantos.logger.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileSystemLogger implements Logger {

    private final Path filePath;

    public FileSystemLogger(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void log(LogMessage message) {
        String entry = "[%s] [%s] %s%n".formatted(
                message.timestamp(),
                message.level(),
                message.content()
        );
        try {
            Files.writeString(filePath, entry, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write log entry to file", e);
        }
    }
}


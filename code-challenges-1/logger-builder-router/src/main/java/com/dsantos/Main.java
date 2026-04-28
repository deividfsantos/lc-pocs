package com.dsantos;

import com.dsantos.logger.Logger;
import com.dsantos.logger.async.AsyncLoggerWrapper;
import com.dsantos.logger.builder.LoggerBuilder;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        Logger syncLogger = new LoggerBuilder()
                .withConsole()
                .withFileSystem(Path.of("app.log"))
                .build();

        syncLogger.info("Application started");
        syncLogger.debug("Loading configuration");
        syncLogger.warn("Config value not found, using default");
        syncLogger.error("Something went wrong");

        AsyncLoggerWrapper asyncLogger = (AsyncLoggerWrapper) new LoggerBuilder()
                .withConsole()
                .withFileSystem(Path.of("async.log"))
                .async()
                .build();

        try (asyncLogger) {
            asyncLogger.info("Async log entry one");
            asyncLogger.info("Async log entry two");
        }
    }
}


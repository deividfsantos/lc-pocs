package com.dsantos.logger;

import com.dsantos.logger.builder.LoggerBuilder;
import com.dsantos.logger.impl.ConsoleLogger;
import com.dsantos.logger.impl.FileSystemLogger;
import com.dsantos.logger.router.LoggerRouter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LoggerBuilderTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldLogToFileSystem() throws Exception {
        Path logFile = tempDir.resolve("app.log");

        Logger logger = new LoggerBuilder()
                .withFileSystem(logFile)
                .build();

        logger.info("hello from test");

        String content = Files.readString(logFile);
        assertTrue(content.contains("hello from test"));
        assertTrue(content.contains("INFO"));
    }

    @Test
    void shouldLogToConsoleWithoutException() {
        Logger logger = new LoggerBuilder()
                .withConsole()
                .build();

        assertDoesNotThrow(() -> logger.debug("debug to console"));
    }

    @Test
    void shouldRouteToMultipleLoggers() throws Exception {
        Path logFile = tempDir.resolve("multi.log");

        Logger logger = new LoggerBuilder()
                .withConsole()
                .withFileSystem(logFile)
                .build();

        logger.warn("routing test");

        String content = Files.readString(logFile);
        assertTrue(content.contains("routing test"));
        assertTrue(content.contains("WARN"));
    }

    @Test
    void shouldBuildAsyncLogger() throws Exception {
        Path logFile = tempDir.resolve("async.log");

        Logger logger = new LoggerBuilder()
                .withFileSystem(logFile)
                .async()
                .build();

        logger.info("async entry");

        Thread.sleep(300);

        String content = Files.readString(logFile);
        assertTrue(content.contains("async entry"));
    }

    @Test
    void shouldThrowWhenNoDestinationConfigured() {
        assertThrows(IllegalStateException.class, () -> new LoggerBuilder().build());
    }

    @Test
    void singleLoggerShouldNotBeWrappedInRouter() {
        Logger logger = new LoggerBuilder()
                .withConsole()
                .build();

        assertInstanceOf(ConsoleLogger.class, logger);
    }

    @Test
    void multipleLoggersShouldBeWrappedInRouter() throws Exception {
        Path logFile = tempDir.resolve("router.log");

        Logger logger = new LoggerBuilder()
                .withConsole()
                .withFileSystem(logFile)
                .build();

        assertInstanceOf(LoggerRouter.class, logger);
    }
}


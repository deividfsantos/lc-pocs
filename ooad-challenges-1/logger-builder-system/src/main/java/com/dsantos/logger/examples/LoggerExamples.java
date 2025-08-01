package com.dsantos.logger.examples;

import com.dsantos.logger.builder.LoggerBuilder;
import com.dsantos.logger.core.Logger;
import com.dsantos.logger.model.LogLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * Examples of using the logging system
 */
public class LoggerExamples {

    public static void main(String[] args) {
        // Example 1: Simple console logger
        simpleConsoleExample();

        // Example 2: File logger
        fileLoggerExample();

        // Example 3: Asynchronous logger
        asyncLoggerExample();

        // Example 4: Logger with multiple destinations
        multiAppenderExample();

        // Example 5: Elasticsearch logger
        elasticsearchExample();

        // Example 6: Complete logger with all destinations
        fullLoggerExample();

        // Wait a bit for asynchronous processing
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void simpleConsoleExample() {
        System.out.println("=== Example 1: Simple Console Logger ===");

        Logger logger = LoggerBuilder.simple("SimpleLogger");

        logger.info("Application started");
        logger.debug("Debug message - won't appear (default level is INFO)");
        logger.warn("Important warning");
        logger.error("Simulated error");

        logger.close();
    }

    private static void fileLoggerExample() {
        System.out.println("\n=== Example 2: File Logger ===");

        Logger logger = LoggerBuilder.create("FileLogger")
                .level(LogLevel.DEBUG)
                .file("logs/application.log", "5MB", 3)
                .build();

        logger.debug("Debug message saved to file");
        logger.info("Information saved to file");
        logger.error("Error saved to file");

        logger.flush();
        logger.close();
    }

    private static void asyncLoggerExample() {
        System.out.println("\n=== Example 3: Asynchronous Logger ===");

        Logger logger = LoggerBuilder.create("AsyncLogger")
                .async()
                .asyncBufferSize(500)
                .asyncFlushInterval(1000)
                .console()
                .file("logs/async.log")
                .build();

        // Simulate multiple messages quickly
        for (int i = 0; i < 10; i++) {
            logger.info("Asynchronous message number %d", i);
        }

        logger.flush();
        logger.close();
    }

    private static void multiAppenderExample() {
        System.out.println("\n=== Example 4: Multiple Destinations ===");

        Logger logger = LoggerBuilder.create("MultiLogger")
                .level(LogLevel.DEBUG)
                .console(LogLevel.WARN)  // Console only for warnings and errors
                .file("logs/all.log")    // File for all logs
                .file("logs/errors.log", LogLevel.ERROR) // File only for errors
                .build();

        logger.debug("Debug - only in all.log file");
        logger.info("Info - only in all.log file");
        logger.warn("Warning - console and all.log file");
        logger.error("Error - console, all.log and errors.log");

        logger.flush();
        logger.close();
    }

    private static void elasticsearchExample() {
        System.out.println("\n=== Example 5: Elasticsearch ===");

        Logger logger = LoggerBuilder.create("ElkLogger")
                .elasticsearch("localhost", 9200, "app-logs-2025")
                .console() // Also to console to see what's being sent
                .build();

        // Log with metadata
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("userId", "12345");
        metadata.put("action", "login");
        metadata.put("ip", "192.168.1.100");

        logger.info("User logged in", metadata);
        logger.error("Authentication failure", metadata);

        // Log with exception
        try {
            throw new RuntimeException("Simulated error");
        } catch (Exception e) {
            logger.error("Exception caught", e);
        }

        logger.flush();
        logger.close();
    }

    private static void fullLoggerExample() {
        System.out.println("\n=== Example 6: Complete Logger ===");

        Logger logger = LoggerBuilder.create("FullLogger")
                .level(LogLevel.DEBUG)
                .async()
                .pattern("[%timestamp] [%level] %logger - %message")
                .property("app.name", "LoggerDemo")
                .property("app.version", "1.0.0")
                .console(LogLevel.WARN)
                .file("logs/full.log", "10MB", 5)
                .file("logs/errors-only.log", LogLevel.ERROR)
                .elasticsearch("localhost", 9200, "full-logs", LogLevel.INFO, 10)
                .build();

        logger.debug("Application initializing...");
        logger.info("Configuration loaded");
        logger.warn("Cache configuration not found, using default");
        logger.error("Failed to connect to database");

        // Structured log
        Map<String, Object> context = new HashMap<>();
        context.put("operation", "user_registration");
        context.put("duration_ms", 150);
        context.put("success", true);

        logger.info("Operation completed", context);

        logger.flush();
        logger.close();
    }
}

package com.dsantos.logger.demo;

import com.dsantos.logger.builder.LoggerBuilder;
import com.dsantos.logger.core.Logger;
import com.dsantos.logger.model.LogLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple demonstration of the logging system
 */
public class SimpleDemo {

    public static void main(String[] args) {
        System.out.println("=== LOGGER BUILDER ROUTER SYSTEM DEMONSTRATION ===\n");

        // 1. Basic console logger
        System.out.println("1. Basic console logger:");
        Logger consoleLogger = LoggerBuilder.simple("ConsoleDemo");
        consoleLogger.info("System started successfully!");
        consoleLogger.warn("Warning: Default configuration being used");
        consoleLogger.error("Simulated error for demonstration");
        consoleLogger.close();

        // 2. File logger with rotation
        System.out.println("\n2. File logger:");
        Logger fileLogger = LoggerBuilder.create("FileDemo")
                .level(LogLevel.DEBUG)
                .file("demo.log", "1MB", 3)
                .build();

        fileLogger.debug("Debug log saved to file");
        fileLogger.info("Operation completed successfully");
        fileLogger.flush();
        fileLogger.close();

        // 3. Asynchronous logger for high performance
        System.out.println("\n3. Asynchronous logger:");
        Logger asyncLogger = LoggerBuilder.create("AsyncDemo")
                .async()
                .asyncBufferSize(100)
                .console()
                .build();

        for (int i = 1; i <= 5; i++) {
            asyncLogger.info("Processing item %d of 5", i);
        }
        asyncLogger.flush();
        asyncLogger.close();

        // 4. Logger with multiple destinations and filters
        System.out.println("\n4. Logger with multiple destinations:");
        Logger multiLogger = LoggerBuilder.create("MultiDemo")
                .level(LogLevel.DEBUG)
                .console(LogLevel.WARN)  // Console only for warnings+
                .file("multi-all.log")   // File for all
                .file("multi-errors.log", LogLevel.ERROR) // File only for errors
                .build();

        multiLogger.debug("Debug - only goes to multi-all.log");
        multiLogger.info("Info - only goes to multi-all.log");
        multiLogger.warn("Warning - goes to console and multi-all.log");
        multiLogger.error("Error - goes to console, multi-all.log and multi-errors.log");
        multiLogger.flush();
        multiLogger.close();

        // 5. Logger with structured metadata
        System.out.println("\n5. Logger with metadata:");
        Logger metadataLogger = LoggerBuilder.create("MetadataDemo")
                .pattern("[%timestamp] [%level] %logger - %message")
                .console()
                .build();

        Map<String, Object> userContext = new HashMap<>();
        userContext.put("userId", "12345");
        userContext.put("sessionId", "abc-def-ghi");
        userContext.put("action", "user_login");

        metadataLogger.info("User logged in", userContext);

        // Example with exception
        try {
            throw new RuntimeException("Example error");
        } catch (Exception e) {
            metadataLogger.error("Operation failed", e);
        }

        metadataLogger.close();

        System.out.println("\n=== DEMONSTRATION COMPLETED ===");
        System.out.println("Log files created: demo.log, multi-all.log, multi-errors.log");

        // Wait for asynchronous processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

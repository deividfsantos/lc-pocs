package com.dsantos.logger.demo;

import com.dsantos.logger.builder.LoggerBuilder;
import com.dsantos.logger.core.Logger;
import com.dsantos.logger.model.LogLevel;

/**
 * Simple demonstration of the logging system with Console and File only (without ELK)
 */
public class GradleDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRATION WITH GRADLE KOTLIN DSL ===\n");

        // 1. Basic console logger
        System.out.println("1. Basic console logger:");
        Logger consoleLogger = LoggerBuilder.simple("GradleConsoleDemo");
        consoleLogger.info("System started with Gradle + Kotlin DSL!");
        consoleLogger.warn("Default configuration being used");
        consoleLogger.error("Simulated error for demonstration");
        consoleLogger.close();

        // 2. File logger
        System.out.println("\n2. File logger:");
        Logger fileLogger = LoggerBuilder.create("GradleFileDemo")
                .level(LogLevel.DEBUG)
                .file("gradle-demo.log", "1MB", 3)
                .build();

        fileLogger.debug("Debug log saved to file via Gradle");
        fileLogger.info("Operation completed successfully using Gradle build");
        fileLogger.flush();
        fileLogger.close();

        // 3. Asynchronous logger
        System.out.println("\n3. Asynchronous logger:");
        Logger asyncLogger = LoggerBuilder.create("GradleAsyncDemo")
                .async()
                .asyncBufferSize(100)
                .console()
                .build();

        for (int i = 1; i <= 5; i++) {
            asyncLogger.info("Processing item %d of 5 with Gradle", i);
        }
        asyncLogger.flush();
        asyncLogger.close();

        // 4. Logger with multiple destinations
        System.out.println("\n4. Logger with multiple destinations:");
        Logger multiLogger = LoggerBuilder.create("GradleMultiDemo")
                .level(LogLevel.DEBUG)
                .console(LogLevel.WARN)  // Console only for warnings+
                .file("gradle-all.log")   // File for all
                .file("gradle-errors.log", LogLevel.ERROR) // File only for errors
                .build();

        multiLogger.debug("Debug - only goes to gradle-all.log");
        multiLogger.info("Info - only goes to gradle-all.log");
        multiLogger.warn("Warning - goes to console and gradle-all.log");
        multiLogger.error("Error - goes to console, gradle-all.log and gradle-errors.log");
        multiLogger.flush();
        multiLogger.close();

        System.out.println("\n=== GRADLE KOTLIN DSL WORKING PERFECTLY! ===");
        System.out.println("Files created: gradle-demo.log, gradle-all.log, gradle-errors.log");

        // Wait for asynchronous processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

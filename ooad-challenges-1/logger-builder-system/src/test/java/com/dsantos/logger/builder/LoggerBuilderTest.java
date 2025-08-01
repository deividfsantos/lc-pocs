package com.dsantos.logger.builder;

import com.dsantos.logger.core.Logger;
import com.dsantos.logger.model.LogLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggerBuilderTest {

    @Test
    void testSimpleBuilder() {
        Logger logger = LoggerBuilder.simple("TestLogger");

        assertNotNull(logger);
        assertEquals("TestLogger", logger.getName());
        assertFalse(logger.getConfig().isAsyncMode());
        assertEquals(LogLevel.INFO, logger.getConfig().getLevel());
    }

    @Test
    void testFluentBuilding() {
        Logger logger = LoggerBuilder.create("FluentLogger")
                .level(LogLevel.DEBUG)
                .async()
                .asyncBufferSize(200)
                .pattern("[%level] %message")
                .property("test.prop", "test.value")
                .console()
                .build();

        assertNotNull(logger);
        assertEquals("FluentLogger", logger.getName());
        assertTrue(logger.getConfig().isAsyncMode());
        assertEquals(LogLevel.DEBUG, logger.getConfig().getLevel());
        assertEquals(200, logger.getConfig().getAsyncBufferSize());
        assertEquals("[%level] %message", logger.getConfig().getPattern());
        assertEquals("test.value", logger.getConfig().getProperty("test.prop"));
    }

    @Test
    void testFileLogger() {
        Logger logger = LoggerBuilder.fileLogger("FileLogger", "test.log");

        assertNotNull(logger);
        assertEquals("FileLogger", logger.getName());
        assertFalse(logger.getConfig().isAsyncMode());
    }

    @Test
    void testAsyncFileLogger() {
        Logger logger = LoggerBuilder.asyncFileLogger("AsyncFileLogger", "async-test.log");

        assertNotNull(logger);
        assertEquals("AsyncFileLogger", logger.getName());
        assertTrue(logger.getConfig().isAsyncMode());
    }

    @Test
    void testBuilderValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            LoggerBuilder.create("").build();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            LoggerBuilder.create(null).build();
        });
    }
}

package com.dsantos.logger.core;

import com.dsantos.logger.appender.LogAppender;
import com.dsantos.logger.config.LoggerConfig;
import com.dsantos.logger.model.LogEntry;
import com.dsantos.logger.model.LogLevel;
import com.dsantos.logger.router.LogRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    private TestAppender testAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        testAppender = new TestAppender();
        LogRouter router = new LogRouter();
        router.addAppender(testAppender);

        LoggerConfig config = new LoggerConfig().setLevel(LogLevel.DEBUG);
        logger = new Logger("TestLogger", router, config);
    }

    @Test
    void testBasicLogging() {
        logger.info("Test message");

        assertEquals(1, testAppender.getLogEntries().size());
        LogEntry entry = testAppender.getLogEntries().getFirst();
        assertEquals("Test message", entry.getMessage());
        assertEquals(LogLevel.INFO, entry.getLevel());
        assertEquals("TestLogger", entry.getLogger());
    }

    @Test
    void testLogLevelFiltering() {
        // Set level to WARN
        LoggerConfig config = new LoggerConfig().setLevel(LogLevel.WARN);
        logger.setConfig(config);

        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warn message");
        logger.error("Error message");

        assertEquals(2, testAppender.getLogEntries().size());
        assertEquals(LogLevel.WARN, testAppender.getLogEntries().get(0).getLevel());
        assertEquals(LogLevel.ERROR, testAppender.getLogEntries().get(1).getLevel());
    }

    @Test
    void testMessageFormatting() {
        logger.info("User %s logged in with ID %d", "john", 123);

        LogEntry entry = testAppender.getLogEntries().getFirst();
        assertEquals("User john logged in with ID 123", entry.getMessage());
    }

    @Test
    void testExceptionLogging() {
        RuntimeException ex = new RuntimeException("Test exception");
        logger.error("An error occurred", ex);

        LogEntry entry = testAppender.getLogEntries().getFirst();
        assertEquals("An error occurred", entry.getMessage());
        assertEquals(ex, entry.getException());
    }

    @Test
    void testMetadataLogging() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("userId", "12345");
        metadata.put("action", "login");

        logger.info("User action", metadata);

        LogEntry entry = testAppender.getLogEntries().getFirst();
        assertEquals(metadata, entry.getMetadata());
    }

    @Test
    void testLevelChecking() {
        LoggerConfig config = new LoggerConfig().setLevel(LogLevel.WARN);
        logger.setConfig(config);

        assertFalse(logger.isDebugEnabled());
        assertFalse(logger.isInfoEnabled());
        assertTrue(logger.isWarnEnabled());
        assertTrue(logger.isErrorEnabled());
        assertTrue(logger.isFatalEnabled());
    }

    // Test appender implementation
    private static class TestAppender implements LogAppender {
        private final List<LogEntry> logEntries = new ArrayList<>();
        private LoggerConfig config;

        @Override
        public void configure(LoggerConfig config) {
            this.config = config;
        }

        @Override
        public void append(LogEntry logEntry) {
            logEntries.add(logEntry);
        }

        @Override
        public void flush() {
            // No-op for testing
        }

        @Override
        public void close() {
            logEntries.clear();
        }

        @Override
        public String getName() {
            return "TestAppender";
        }

        @Override
        public boolean isAsync() {
            return config != null && config.isAsyncMode();
        }

        public List<LogEntry> getLogEntries() {
            return new ArrayList<>(logEntries);
        }
    }
}

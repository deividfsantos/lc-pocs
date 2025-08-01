package com.dsantos.logger.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Representa uma entrada de log com todas as informações necessárias
 */
public class LogEntry {
    private final String message;
    private final LogLevel level;
    private final LocalDateTime timestamp;
    private final String logger;
    private final Map<String, Object> metadata;
    private final Throwable exception;

    private LogEntry(Builder builder) {
        this.message = builder.message;
        this.level = builder.level;
        this.timestamp = builder.timestamp != null ? builder.timestamp : LocalDateTime.now();
        this.logger = builder.logger;
        this.metadata = builder.metadata;
        this.exception = builder.exception;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getLogger() {
        return logger;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public Throwable getException() {
        return exception;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private LogLevel level;
        private LocalDateTime timestamp;
        private String logger;
        private Map<String, Object> metadata;
        private Throwable exception;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder level(LogLevel level) {
            this.level = level;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder logger(String logger) {
            this.logger = logger;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder exception(Throwable exception) {
            this.exception = exception;
            return this;
        }

        public LogEntry build() {
            if (message == null || level == null) {
                throw new IllegalArgumentException("Message and level are required");
            }
            return new LogEntry(this);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s: %s",
                timestamp, level, logger != null ? logger : "UNKNOWN", message);
    }
}

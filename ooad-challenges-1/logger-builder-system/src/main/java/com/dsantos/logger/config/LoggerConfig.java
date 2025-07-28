package com.dsantos.logger.config;

import com.dsantos.logger.model.LogLevel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggerConfig {
    private LogLevel level = LogLevel.INFO;
    private boolean asyncMode = false;
    private int asyncBufferSize = 1000;
    private int asyncFlushInterval = 5000; // milliseconds
    private String pattern = "[%timestamp] %level - %logger: %message";
    private final Map<String, Object> properties = new ConcurrentHashMap<>();

    public LogLevel getLevel() {
        return level;
    }

    public LoggerConfig setLevel(LogLevel level) {
        this.level = level;
        return this;
    }

    public boolean isAsyncMode() {
        return asyncMode;
    }

    public LoggerConfig setAsyncMode(boolean asyncMode) {
        this.asyncMode = asyncMode;
        return this;
    }

    public int getAsyncBufferSize() {
        return asyncBufferSize;
    }

    public LoggerConfig setAsyncBufferSize(int asyncBufferSize) {
        this.asyncBufferSize = asyncBufferSize;
        return this;
    }

    public int getAsyncFlushInterval() {
        return asyncFlushInterval;
    }

    public LoggerConfig setAsyncFlushInterval(int asyncFlushInterval) {
        this.asyncFlushInterval = asyncFlushInterval;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public LoggerConfig setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public LoggerConfig setProperty(String key, Object value) {
        this.properties.put(key, value);
        return this;
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public Object getProperty(String key, Object defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }
}

package com.dsantos.logger;

import java.time.Instant;

public record LogMessage(LogLevel level, String content, Instant timestamp) {

    public LogMessage(LogLevel level, String content) {
        this(level, content, Instant.now());
    }
}


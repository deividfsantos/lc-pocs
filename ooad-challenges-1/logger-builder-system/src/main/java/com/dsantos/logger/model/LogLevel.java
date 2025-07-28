package com.dsantos.logger.model;

/**
 * Níveis de log disponíveis no sistema
 */
public enum LogLevel {
    TRACE(0),
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4),
    FATAL(5);

    private final int priority;

    LogLevel(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isEnabled(LogLevel threshold) {
        return this.priority >= threshold.priority;
    }
}

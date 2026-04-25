package com.dsantos.logger;

public interface Logger {

    void log(LogMessage message);

    default void log(LogLevel level, String content) {
        log(new LogMessage(level, content));
    }

    default void debug(String content) {
        log(LogLevel.DEBUG, content);
    }

    default void info(String content) {
        log(LogLevel.INFO, content);
    }

    default void warn(String content) {
        log(LogLevel.WARN, content);
    }

    default void error(String content) {
        log(LogLevel.ERROR, content);
    }
}


package com.dsantos.logger.core;

import com.dsantos.logger.config.LoggerConfig;
import com.dsantos.logger.model.LogEntry;
import com.dsantos.logger.model.LogLevel;
import com.dsantos.logger.router.LogRouter;

import java.util.Map;

public class Logger {
    private final String name;
    private final LogRouter router;
    private LoggerConfig config;

    public Logger(String name, LogRouter router) {
        this.name = name;
        this.router = router;
        this.config = new LoggerConfig();
    }

    public Logger(String name, LogRouter router, LoggerConfig config) {
        this.name = name;
        this.router = router;
        this.config = config != null ? config : new LoggerConfig();
    }

    // ============= TRACE LEVEL =============
    
    public void trace(String message) {
        log(LogLevel.TRACE, message, null, null);
    }

    public void trace(String message, Object... args) {
        log(LogLevel.TRACE, format(message, args), null, null);
    }

    public void trace(String message, Throwable throwable) {
        log(LogLevel.TRACE, message, null, throwable);
    }

    public void trace(String message, Map<String, Object> metadata) {
        log(LogLevel.TRACE, message, metadata, null);
    }

    // ============= DEBUG LEVEL =============
    
    public void debug(String message) {
        log(LogLevel.DEBUG, message, null, null);
    }

    public void debug(String message, Object... args) {
        log(LogLevel.DEBUG, format(message, args), null, null);
    }

    public void debug(String message, Throwable throwable) {
        log(LogLevel.DEBUG, message, null, throwable);
    }

    public void debug(String message, Map<String, Object> metadata) {
        log(LogLevel.DEBUG, message, metadata, null);
    }

    // ============= INFO LEVEL =============
    
    public void info(String message) {
        log(LogLevel.INFO, message, null, null);
    }

    public void info(String message, Object... args) {
        log(LogLevel.INFO, format(message, args), null, null);
    }

    public void info(String message, Throwable throwable) {
        log(LogLevel.INFO, message, null, throwable);
    }

    public void info(String message, Map<String, Object> metadata) {
        log(LogLevel.INFO, message, metadata, null);
    }
    
    public void warn(String message) {
        log(LogLevel.WARN, message, null, null);
    }

    public void warn(String message, Object... args) {
        log(LogLevel.WARN, format(message, args), null, null);
    }

    public void warn(String message, Throwable throwable) {
        log(LogLevel.WARN, message, null, throwable);
    }

    public void warn(String message, Map<String, Object> metadata) {
        log(LogLevel.WARN, message, metadata, null);
    }
    
    public void error(String message) {
        log(LogLevel.ERROR, message, null, null);
    }

    public void error(String message, Object... args) {
        log(LogLevel.ERROR, format(message, args), null, null);
    }

    public void error(String message, Throwable throwable) {
        log(LogLevel.ERROR, message, null, throwable);
    }

    public void error(String message, Map<String, Object> metadata) {
        log(LogLevel.ERROR, message, metadata, null);
    }

    // ============= FATAL LEVEL =============
    
    public void fatal(String message) {
        log(LogLevel.FATAL, message, null, null);
    }

    public void fatal(String message, Object... args) {
        log(LogLevel.FATAL, format(message, args), null, null);
    }

    public void fatal(String message, Throwable throwable) {
        log(LogLevel.FATAL, message, null, throwable);
    }

    public void fatal(String message, Map<String, Object> metadata) {
        log(LogLevel.FATAL, message, metadata, null);
    }

    // ============= CORE LOGGING METHOD =============
    
    private void log(LogLevel level, String message, Map<String, Object> metadata, Throwable throwable) {
        // Check if level is enabled
        if (!level.isEnabled(config.getLevel())) {
            return;
        }

        // Build log entry
        LogEntry logEntry = LogEntry.builder()
            .message(message)
            .level(level)
            .logger(name)
            .metadata(metadata)
            .exception(throwable)
            .build();

        router.route(logEntry);
    }
    
    private String format(String message, Object... args) {
        if (args == null || args.length == 0) {
            return message;
        }
        
        try {
            return String.format(message, args);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder(message);
            sb.append(" [");
            for (int i = 0; i < args.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(args[i]);
            }
            sb.append("]");
            return sb.toString();
        }
    }
    
    public boolean isTraceEnabled() {
        return LogLevel.TRACE.isEnabled(config.getLevel());
    }

    public boolean isDebugEnabled() {
        return LogLevel.DEBUG.isEnabled(config.getLevel());
    }

    public boolean isInfoEnabled() {
        return LogLevel.INFO.isEnabled(config.getLevel());
    }

    public boolean isWarnEnabled() {
        return LogLevel.WARN.isEnabled(config.getLevel());
    }

    public boolean isErrorEnabled() {
        return LogLevel.ERROR.isEnabled(config.getLevel());
    }

    public boolean isFatalEnabled() {
        return LogLevel.FATAL.isEnabled(config.getLevel());
    }
    
    public String getName() {
        return name;
    }

    public LoggerConfig getConfig() {
        return config;
    }

    public void setConfig(LoggerConfig config) {
        this.config = config != null ? config : new LoggerConfig();
    }

    public void flush() {
        router.flush();
    }

    public void close() {
        router.close();
    }
}

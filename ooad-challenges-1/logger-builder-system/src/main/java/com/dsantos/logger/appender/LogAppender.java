package com.dsantos.logger.appender;

import com.dsantos.logger.config.LoggerConfig;
import com.dsantos.logger.model.LogEntry;

public interface LogAppender {

    void configure(LoggerConfig config);

    void append(LogEntry logEntry);

    void flush();

    void close();

    String getName();

    boolean isAsync();
}

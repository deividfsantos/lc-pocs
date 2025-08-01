package com.dsantos.logger.formatter;

import com.dsantos.logger.model.LogEntry;

/**
 * Interface for log formatters
 */
public interface LogFormatter {

    /**
     * Formats a log entry into a string
     */
    String format(LogEntry logEntry);
}

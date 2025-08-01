package com.dsantos.logger.formatter;

import com.dsantos.logger.model.LogEntry;

/**
 * Interface para formatadores de log
 */
public interface LogFormatter {

    /**
     * Formata uma entrada de log em string
     */
    String format(LogEntry logEntry);
}

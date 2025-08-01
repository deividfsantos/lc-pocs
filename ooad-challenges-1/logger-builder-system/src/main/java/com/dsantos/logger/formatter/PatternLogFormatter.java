package com.dsantos.logger.formatter;

import com.dsantos.logger.model.LogEntry;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;

/**
 * Formatador que usa padrões para formatar logs
 */
public class PatternLogFormatter implements LogFormatter {
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final String pattern;
    private final DateTimeFormatter dateFormatter;

    public PatternLogFormatter(String pattern) {
        this.pattern = pattern != null ? pattern : "[%timestamp] %level - %logger: %message";
        this.dateFormatter = DEFAULT_DATE_FORMAT;
    }

    public PatternLogFormatter(String pattern, DateTimeFormatter dateFormatter) {
        this.pattern = pattern != null ? pattern : "[%timestamp] %level - %logger: %message";
        this.dateFormatter = dateFormatter != null ? dateFormatter : DEFAULT_DATE_FORMAT;
    }

    @Override
    public String format(LogEntry logEntry) {
        String result = pattern;

        // Replace timestamp
        result = result.replace("%timestamp",
                logEntry.getTimestamp().format(dateFormatter));

        // Replace level
        result = result.replace("%level", logEntry.getLevel().toString());

        // Replace logger name
        result = result.replace("%logger",
                logEntry.getLogger() != null ? logEntry.getLogger() : "UNKNOWN");

        // Replace message
        result = result.replace("%message", logEntry.getMessage());

        // Add exception if present
        if (logEntry.getException() != null) {
            result += System.lineSeparator() + formatException(logEntry.getException());
        }

        return result;
    }

    private String formatException(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}

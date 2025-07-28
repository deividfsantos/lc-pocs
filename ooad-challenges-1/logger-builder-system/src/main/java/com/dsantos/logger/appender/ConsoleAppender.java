package com.dsantos.logger.appender;

import com.dsantos.logger.config.LoggerConfig;
import com.dsantos.logger.formatter.LogFormatter;
import com.dsantos.logger.formatter.PatternLogFormatter;
import com.dsantos.logger.model.LogEntry;
import com.dsantos.logger.model.LogLevel;

import java.io.PrintStream;

public class ConsoleAppender extends AbstractLogAppender {
    private static final String USE_STDERR_PROPERTY = "console.useStderr";
    private static final String ERROR_THRESHOLD_PROPERTY = "console.errorThreshold";
    
    private LogFormatter formatter;
    private boolean useStderr = false;
    private LogLevel errorThreshold = LogLevel.ERROR;

    public ConsoleAppender() {
        super("ConsoleAppender");
    }

    @Override
    public void configure(LoggerConfig config) {
        super.configure(config);
        
        // Initialize formatter
        this.formatter = new PatternLogFormatter(config.getPattern());
        
        // Configure stderr usage
        Object useStderrObj = config.getProperty(USE_STDERR_PROPERTY);
        if (useStderrObj instanceof Boolean) {
            this.useStderr = (Boolean) useStderrObj;
        }
        
        // Configure error threshold
        Object errorThresholdObj = config.getProperty(ERROR_THRESHOLD_PROPERTY);
        if (errorThresholdObj instanceof LogLevel) {
            this.errorThreshold = (LogLevel) errorThresholdObj;
        } else if (errorThresholdObj instanceof String) {
            try {
                this.errorThreshold = LogLevel.valueOf(((String) errorThresholdObj).toUpperCase());
            } catch (IllegalArgumentException e) {
                // Keep default
            }
        }
    }

    @Override
    protected void doAppend(LogEntry logEntry) {
        String formattedMessage = formatter.format(logEntry);
        
        PrintStream output = getOutputStream(logEntry.getLevel());
        output.println(formattedMessage);
        
        if (!isAsync()) {
            output.flush(); // Immediate flush for sync mode
        }
    }

    private PrintStream getOutputStream(LogLevel level) {
        if (useStderr || level.getPriority() >= errorThreshold.getPriority()) {
            return System.err;
        }
        return System.out;
    }

    @Override
    protected void doFlush() {
        System.out.flush();
        System.err.flush();
    }
}

package com.dsantos.logger.router;

import com.dsantos.logger.appender.LogAppender;
import com.dsantos.logger.model.LogEntry;
import com.dsantos.logger.model.LogLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 * Router that decides which appenders to send each log to
 */
public class LogRouter {
    private final List<AppenderRule> rules = new CopyOnWriteArrayList<>();

    /**
     * Adds a routing rule
     */
    public LogRouter addRule(LogAppender appender, Predicate<LogEntry> condition) {
        rules.add(new AppenderRule(appender, condition));
        return this;
    }

    /**
     * Adds an appender that receives all logs
     */
    public LogRouter addAppender(LogAppender appender) {
        return addRule(appender, entry -> true);
    }

    /**
     * Adds an appender with minimum level filter
     */
    public LogRouter addAppender(LogAppender appender, LogLevel minLevel) {
        return addRule(appender, entry -> entry.getLevel().isEnabled(minLevel));
    }

    /**
     * Adds an appender with logger name filter
     */
    public LogRouter addAppender(LogAppender appender, String loggerNamePattern) {
        return addRule(appender, entry -> {
            String loggerName = entry.getLogger();
            return loggerName != null && loggerName.matches(loggerNamePattern);
        });
    }

    /**
     * Routes a log entry to the appropriate appenders
     */
    public void route(LogEntry logEntry) {
        for (AppenderRule rule : rules) {
            if (rule.condition.test(logEntry)) {
                try {
                    rule.appender.append(logEntry);
                } catch (Exception e) {
                    // Log routing errors to stderr to avoid infinite loops
                    System.err.println("Error routing log to appender " +
                            rule.appender.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Forces flush on all appenders
     */
    public void flush() {
        for (AppenderRule rule : rules) {
            try {
                rule.appender.flush();
            } catch (Exception e) {
                System.err.println("Error flushing appender " +
                        rule.appender.getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Closes all appenders
     */
    public void close() {
        for (AppenderRule rule : rules) {
            try {
                rule.appender.close();
            } catch (Exception e) {
                System.err.println("Error closing appender " +
                        rule.appender.getName() + ": " + e.getMessage());
            }
        }
        rules.clear();
    }

    /**
     * Returns all registered appenders
     */
    public List<LogAppender> getAppenders() {
        List<LogAppender> appenders = new ArrayList<>();
        for (AppenderRule rule : rules) {
            appenders.add(rule.appender);
        }
        return appenders;
    }

    private static class AppenderRule {
        final LogAppender appender;
        final Predicate<LogEntry> condition;

        AppenderRule(LogAppender appender, Predicate<LogEntry> condition) {
            this.appender = appender;
            this.condition = condition;
        }
    }
}

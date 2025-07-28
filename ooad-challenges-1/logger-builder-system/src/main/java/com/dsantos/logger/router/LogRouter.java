package com.dsantos.logger.router;

import com.dsantos.logger.appender.LogAppender;
import com.dsantos.logger.model.LogEntry;
import com.dsantos.logger.model.LogLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 * Roteador que decide para quais appenders enviar cada log
 */
public class LogRouter {
    private final List<AppenderRule> rules = new CopyOnWriteArrayList<>();

    /**
     * Adiciona uma regra de roteamento
     */
    public LogRouter addRule(LogAppender appender, Predicate<LogEntry> condition) {
        rules.add(new AppenderRule(appender, condition));
        return this;
    }

    /**
     * Adiciona um appender que recebe todos os logs
     */
    public LogRouter addAppender(LogAppender appender) {
        return addRule(appender, entry -> true);
    }

    /**
     * Adiciona um appender com filtro de nível mínimo
     */
    public LogRouter addAppender(LogAppender appender, LogLevel minLevel) {
        return addRule(appender, entry -> entry.getLevel().isEnabled(minLevel));
    }

    /**
     * Adiciona um appender com filtro de logger name
     */
    public LogRouter addAppender(LogAppender appender, String loggerNamePattern) {
        return addRule(appender, entry -> {
            String loggerName = entry.getLogger();
            return loggerName != null && loggerName.matches(loggerNamePattern);
        });
    }

    /**
     * Roteia uma entrada de log para os appenders apropriados
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
     * Força flush em todos os appenders
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
     * Fecha todos os appenders
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
     * Retorna todos os appenders registrados
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

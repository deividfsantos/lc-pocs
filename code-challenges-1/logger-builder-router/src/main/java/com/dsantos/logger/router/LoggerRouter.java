package com.dsantos.logger.router;

import com.dsantos.logger.LogMessage;
import com.dsantos.logger.Logger;

import java.util.List;

public class LoggerRouter implements Logger {

    private final List<Logger> loggers;

    public LoggerRouter(List<Logger> loggers) {
        this.loggers = List.copyOf(loggers);
    }

    @Override
    public void log(LogMessage message) {
        loggers.forEach(logger -> logger.log(message));
    }

    public List<Logger> getLoggers() {
        return loggers;
    }
}


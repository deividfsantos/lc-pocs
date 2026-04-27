package com.dsantos.logger.builder;

import com.dsantos.logger.Logger;
import com.dsantos.logger.async.AsyncLoggerWrapper;
import com.dsantos.logger.impl.ConsoleLogger;
import com.dsantos.logger.impl.ElkLogger;
import com.dsantos.logger.impl.FileSystemLogger;
import com.dsantos.logger.router.LoggerRouter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class LoggerBuilder {

    private final List<Logger> loggers = new ArrayList<>();
    private boolean async = false;
    private ExecutorService customExecutor = null;

    public LoggerBuilder withConsole() {
        loggers.add(new ConsoleLogger());
        return this;
    }

    public LoggerBuilder withFileSystem(Path path) {
        loggers.add(new FileSystemLogger(path));
        return this;
    }

    public LoggerBuilder withElk(String endpoint, String index) {
        loggers.add(new ElkLogger(endpoint, index));
        return this;
    }

    public LoggerBuilder async() {
        this.async = true;
        return this;
    }

    public LoggerBuilder async(ExecutorService executor) {
        this.async = true;
        this.customExecutor = executor;
        return this;
    }

    public Logger build() {
        if (loggers.isEmpty()) {
            throw new IllegalStateException("At least one logger destination must be configured");
        }

        Logger base = loggers.size() == 1 ? loggers.getFirst() : new LoggerRouter(loggers);

        if (async) {
            return customExecutor != null
                    ? new AsyncLoggerWrapper(base, customExecutor)
                    : new AsyncLoggerWrapper(base);
        }

        return base;
    }
}


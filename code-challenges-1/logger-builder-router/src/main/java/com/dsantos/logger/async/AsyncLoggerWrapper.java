package com.dsantos.logger.async;

import com.dsantos.logger.LogMessage;
import com.dsantos.logger.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncLoggerWrapper implements Logger, AutoCloseable {

    private final Logger delegate;
    private final ExecutorService executor;

    public AsyncLoggerWrapper(Logger delegate) {
        this.delegate = delegate;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public AsyncLoggerWrapper(Logger delegate, ExecutorService executor) {
        this.delegate = delegate;
        this.executor = executor;
    }

    @Override
    public void log(LogMessage message) {
        executor.submit(() -> delegate.log(message));
    }

    @Override
    public void close() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}


package com.dsantos.logger.appender;

import com.dsantos.logger.config.LoggerConfig;
import com.dsantos.logger.model.LogEntry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class AbstractLogAppender implements LogAppender {
    protected LoggerConfig config;
    protected final String name;
    protected final AtomicBoolean closed = new AtomicBoolean(false);
    
    // Async support
    private BlockingQueue<LogEntry> asyncBuffer;
    private ExecutorService asyncExecutor;
    private ScheduledExecutorService flushScheduler;

    protected AbstractLogAppender(String name) {
        this.name = name;
    }

    @Override
    public void configure(LoggerConfig config) {
        this.config = config;
        
        if (config.isAsyncMode()) {
            setupAsyncMode();
        }
    }

    private void setupAsyncMode() {
        this.asyncBuffer = new ArrayBlockingQueue<>(config.getAsyncBufferSize());
        this.asyncExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, name + "-async-writer");
            t.setDaemon(true);
            return t;
        });
        
        // Start async writer
        asyncExecutor.submit(this::asyncWriter);
        
        // Setup periodic flush
        this.flushScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, name + "-flush-scheduler");
            t.setDaemon(true);
            return t;
        });
        
        flushScheduler.scheduleAtFixedRate(
            this::flush, 
            config.getAsyncFlushInterval(), 
            config.getAsyncFlushInterval(), 
            TimeUnit.MILLISECONDS
        );
    }

    @Override
    public final void append(LogEntry logEntry) {
        if (closed.get()) {
            return;
        }

        if (config != null && config.isAsyncMode()) {
            // Try to add to async buffer, if full, block or drop based on strategy
            if (!asyncBuffer.offer(logEntry)) {
                // Buffer is full, handle overflow
                handleBufferOverflow(logEntry);
            }
        } else {
            // Synchronous write
            doAppend(logEntry);
        }
    }

    protected void handleBufferOverflow(LogEntry logEntry) {
        // Default: try to put (blocks if buffer is full)
        try {
            asyncBuffer.put(logEntry);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Fallback to direct write
            doAppend(logEntry);
        }
    }

    private void asyncWriter() {
        while (!closed.get() || !asyncBuffer.isEmpty()) {
            try {
                LogEntry entry = asyncBuffer.poll(1, TimeUnit.SECONDS);
                if (entry != null) {
                    doAppend(entry);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                handleAsyncError(e);
            }
        }
    }

    protected void handleAsyncError(Exception e) {
        // Default: print to stderr
        System.err.println("Error in async logger " + name + ": " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * Template method for actual log writing - implementado pelas subclasses
     */
    protected abstract void doAppend(LogEntry logEntry);

    @Override
    public void flush() {
        if (config != null && config.isAsyncMode()) {
            // In async mode, just ensure all buffered entries are processed
            while (!asyncBuffer.isEmpty()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        // Subclasses can override to implement specific flush logic
        doFlush();
    }

    protected void doFlush() {
        // Default implementation does nothing
    }

    @Override
    public void close() {
        if (closed.getAndSet(true)) {
            return;
        }

        if (asyncExecutor != null) {
            try {
                // Stop accepting new entries and process remaining ones
                asyncExecutor.shutdown();
                if (!asyncExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    asyncExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                asyncExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        if (flushScheduler != null) {
            flushScheduler.shutdown();
        }

        doClose();
    }

    protected void doClose() {
        // Default implementation does nothing
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAsync() {
        return config != null && config.isAsyncMode();
    }
}

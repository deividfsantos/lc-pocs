package com.dsantos.logger.builder;

import com.dsantos.logger.appender.ConsoleAppender;
import com.dsantos.logger.appender.FileSystemAppender;
import com.dsantos.logger.appender.LogAppender;
import com.dsantos.logger.config.LoggerConfig;
import com.dsantos.logger.core.Logger;
import com.dsantos.logger.model.LogLevel;
import com.dsantos.logger.router.LogRouter;

public class LoggerBuilder {
    private String name;
    private LoggerConfig config;
    private LogRouter router;

    public LoggerBuilder(String name) {
        this.name = name;
        this.config = new LoggerConfig();
        this.router = new LogRouter();
    }

    public LoggerBuilder level(LogLevel level) {
        this.config.setLevel(level);
        return this;
    }

    public LoggerBuilder async() {
        this.config.setAsyncMode(true);
        return this;
    }

    public LoggerBuilder sync() {
        this.config.setAsyncMode(false);
        return this;
    }

    public LoggerBuilder asyncBufferSize(int size) {
        this.config.setAsyncBufferSize(size);
        return this;
    }

    public LoggerBuilder asyncFlushInterval(int intervalMs) {
        this.config.setAsyncFlushInterval(intervalMs);
        return this;
    }

    public LoggerBuilder pattern(String pattern) {
        this.config.setPattern(pattern);
        return this;
    }

    public LoggerBuilder property(String key, Object value) {
        this.config.setProperty(key, value);
        return this;
    }

    public LoggerBuilder console() {
        ConsoleAppender appender = new ConsoleAppender();
        appender.configure(config);
        router.addAppender(appender);
        return this;
    }

    public LoggerBuilder console(LogLevel minLevel) {
        ConsoleAppender appender = new ConsoleAppender();
        appender.configure(config);
        router.addAppender(appender, minLevel);
        return this;
    }

    public LoggerBuilder file(String filePath) {
        FileSystemAppender appender = new FileSystemAppender(filePath);

        // Set file-specific properties
        LoggerConfig fileConfig = createAppenderConfig();
        fileConfig.setProperty("file.path", filePath);

        appender.configure(fileConfig);
        router.addAppender(appender);
        return this;
    }

    public LoggerBuilder file(String filePath, LogLevel minLevel) {
        FileSystemAppender appender = new FileSystemAppender(filePath);

        LoggerConfig fileConfig = createAppenderConfig();
        fileConfig.setProperty("file.path", filePath);

        appender.configure(fileConfig);
        router.addAppender(appender, minLevel);
        return this;
    }

    public LoggerBuilder file(String filePath, String maxFileSize, int maxBackups) {
        FileSystemAppender appender = new FileSystemAppender(filePath);

        LoggerConfig fileConfig = createAppenderConfig();
        fileConfig.setProperty("file.path", filePath);
        fileConfig.setProperty("file.maxSize", maxFileSize);
        fileConfig.setProperty("file.maxBackups", maxBackups);

        appender.configure(fileConfig);
        router.addAppender(appender);
        return this;
    }

    /* Elasticsearch appender temporariamente comentado devido a problemas de dependência
    public LoggerBuilder elasticsearch(String host, int port, String index) {
        ElasticsearchAppender appender = new ElasticsearchAppender();
        
        LoggerConfig elkConfig = createAppenderConfig();
        elkConfig.setProperty("elasticsearch.host", host);
        elkConfig.setProperty("elasticsearch.port", port);
        elkConfig.setProperty("elasticsearch.index", index);
        
        appender.configure(elkConfig);
        router.addAppender(appender);
        return this;
    }

    public LoggerBuilder elasticsearch(String host, int port, String index, 
                                     String username, String password) {
        ElasticsearchAppender appender = new ElasticsearchAppender();
        
        LoggerConfig elkConfig = createAppenderConfig();
        elkConfig.setProperty("elasticsearch.host", host);
        elkConfig.setProperty("elasticsearch.port", port);
        elkConfig.setProperty("elasticsearch.index", index);
        elkConfig.setProperty("elasticsearch.username", username);
        elkConfig.setProperty("elasticsearch.password", password);
        
        appender.configure(elkConfig);
        router.addAppender(appender);
        return this;
    }

    public LoggerBuilder elasticsearch(String host, int port, String index, 
                                     LogLevel minLevel, int batchSize) {
        ElasticsearchAppender appender = new ElasticsearchAppender();
        
        LoggerConfig elkConfig = createAppenderConfig();
        elkConfig.setProperty("elasticsearch.host", host);
        elkConfig.setProperty("elasticsearch.port", port);
        elkConfig.setProperty("elasticsearch.index", index);
        elkConfig.setProperty("elasticsearch.batchSize", batchSize);
        
        appender.configure(elkConfig);
        router.addAppender(appender, minLevel);
        return this;
    }
    */

    public LoggerBuilder customAppender(LogAppender appender) {
        appender.configure(config);
        router.addAppender(appender);
        return this;
    }

    public LoggerBuilder customAppender(LogAppender appender, LogLevel minLevel) {
        appender.configure(config);
        router.addAppender(appender, minLevel);
        return this;
    }

    // ============= ADVANCED ROUTING =============

    public LoggerBuilder routeByLoggerName(LogAppender appender, String loggerNamePattern) {
        appender.configure(createAppenderConfig());
        router.addAppender(appender, loggerNamePattern);
        return this;
    }

    // ============= BUILD METHOD =============

    public Logger build() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Logger name cannot be null or empty");
        }

        return new Logger(name, router, config);
    }

    private LoggerConfig createAppenderConfig() {
        LoggerConfig appenderConfig = new LoggerConfig();
        appenderConfig.setLevel(config.getLevel());
        appenderConfig.setAsyncMode(config.isAsyncMode());
        appenderConfig.setAsyncBufferSize(config.getAsyncBufferSize());
        appenderConfig.setAsyncFlushInterval(config.getAsyncFlushInterval());
        appenderConfig.setPattern(config.getPattern());

        config.getProperties().forEach(appenderConfig::setProperty);

        return appenderConfig;
    }

    public static LoggerBuilder create(String name) {
        return new LoggerBuilder(name);
    }

    public static Logger simple(String name) {
        return create(name)
                .console()
                .build();
    }

    public static Logger fileLogger(String name, String filePath) {
        return create(name)
                .file(filePath)
                .build();
    }

    public static Logger asyncFileLogger(String name, String filePath) {
        return create(name)
                .async()
                .file(filePath)
                .build();
    }

    /* Static methods temporarily commented due to dependency issues
    public static Logger elkLogger(String name, String host, int port, String index) {
        return create(name)
            .elasticsearch(host, port, index)
            .build();
    }

    public static Logger fullLogger(String name, String filePath, String elkHost, int elkPort, String elkIndex) {
        return create(name)
            .console(LogLevel.WARN)
            .file(filePath)
            .elasticsearch(elkHost, elkPort, elkIndex, LogLevel.INFO, 50)
            .build();
    }
    */
}

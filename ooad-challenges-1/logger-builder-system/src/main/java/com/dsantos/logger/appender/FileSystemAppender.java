package com.dsantos.logger.appender;

import com.dsantos.logger.config.LoggerConfig;
import com.dsantos.logger.formatter.LogFormatter;
import com.dsantos.logger.formatter.PatternLogFormatter;
import com.dsantos.logger.model.LogEntry;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Appender that writes logs to the file system
 */
public class FileSystemAppender extends AbstractLogAppender {
    private static final String DEFAULT_FILE_PATH = "application.log";
    private static final String FILE_PATH_PROPERTY = "file.path";
    private static final String MAX_FILE_SIZE_PROPERTY = "file.maxSize";
    private static final String MAX_BACKUP_FILES_PROPERTY = "file.maxBackups";

    private Path logFilePath;
    private BufferedWriter writer;
    private LogFormatter formatter;
    private long maxFileSize = 10 * 1024 * 1024; // 10MB default
    private int maxBackupFiles = 5;
    private long currentFileSize = 0;

    public FileSystemAppender() {
        super("FileSystemAppender");
    }

    public FileSystemAppender(String filePath) {
        super("FileSystemAppender");
        this.logFilePath = Paths.get(filePath);
    }

    @Override
    public void configure(LoggerConfig config) {
        super.configure(config);

        // Get file path from config
        String filePath = (String) config.getProperty(FILE_PATH_PROPERTY, DEFAULT_FILE_PATH);
        this.logFilePath = Paths.get(filePath);

        // Get max file size
        Object maxSizeObj = config.getProperty(MAX_FILE_SIZE_PROPERTY);
        if (maxSizeObj instanceof Number) {
            this.maxFileSize = ((Number) maxSizeObj).longValue();
        } else if (maxSizeObj instanceof String) {
            this.maxFileSize = parseFileSize((String) maxSizeObj);
        }

        // Get max backup files
        Object maxBackupsObj = config.getProperty(MAX_BACKUP_FILES_PROPERTY);
        if (maxBackupsObj instanceof Number) {
            this.maxBackupFiles = ((Number) maxBackupsObj).intValue();
        }

        // Initialize formatter
        this.formatter = new PatternLogFormatter(config.getPattern());

        // Initialize file writer
        initializeWriter();
    }

    private long parseFileSize(String sizeStr) {
        sizeStr = sizeStr.trim().toUpperCase();
        long multiplier = 1;

        if (sizeStr.endsWith("KB")) {
            multiplier = 1024;
            sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
        } else if (sizeStr.endsWith("MB")) {
            multiplier = 1024 * 1024;
            sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
        } else if (sizeStr.endsWith("GB")) {
            multiplier = 1024 * 1024 * 1024;
            sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
        }

        try {
            return Long.parseLong(sizeStr.trim()) * multiplier;
        } catch (NumberFormatException e) {
            return 10 * 1024 * 1024; // Default 10MB
        }
    }

    private void initializeWriter() {
        try {
            // Create directories if they don't exist
            Path parentDir = logFilePath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            // Get current file size if file exists
            if (Files.exists(logFilePath)) {
                currentFileSize = Files.size(logFilePath);
            }

            // Create writer
            this.writer = Files.newBufferedWriter(logFilePath,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize FileSystemAppender", e);
        }
    }

    @Override
    protected void doAppend(LogEntry logEntry) {
        if (writer == null) {
            return;
        }

        try {
            String formattedMessage = formatter.format(logEntry);

            // Check if we need to rotate the file
            if (currentFileSize + formattedMessage.length() > maxFileSize) {
                rotateLogFile();
            }

            writer.write(formattedMessage);
            writer.newLine();
            currentFileSize += formattedMessage.length() + System.lineSeparator().length();

            if (!isAsync()) {
                writer.flush(); // Immediate flush for sync mode
            }

        } catch (IOException e) {
            handleAsyncError(e);
        }
    }

    private void rotateLogFile() throws IOException {
        // Close current writer
        if (writer != null) {
            writer.close();
        }

        // Rotate backup files
        for (int i = maxBackupFiles - 1; i > 0; i--) {
            Path source = Paths.get(logFilePath.toString() + "." + i);
            Path target = Paths.get(logFilePath.toString() + "." + (i + 1));

            if (Files.exists(source)) {
                Files.move(source, target);
            }
        }

        // Move current log to .1
        if (Files.exists(logFilePath)) {
            Path backup = Paths.get(logFilePath.toString() + ".1");
            Files.move(logFilePath, backup);
        }

        // Create new writer
        currentFileSize = 0;
        this.writer = Files.newBufferedWriter(logFilePath,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    protected void doFlush() {
        if (writer != null) {
            try {
                writer.flush();
            } catch (IOException e) {
                handleAsyncError(e);
            }
        }
    }

    @Override
    protected void doClose() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Error closing FileSystemAppender: " + e.getMessage());
            }
        }
    }
}

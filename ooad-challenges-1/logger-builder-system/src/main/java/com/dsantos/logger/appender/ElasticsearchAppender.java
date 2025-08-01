package com.dsantos.logger.appender;

import com.dsantos.logger.config.LoggerConfig;
import com.dsantos.logger.model.LogEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Appender that sends logs to Elasticsearch
 */
public class ElasticsearchAppender extends AbstractLogAppender {
    private static final String HOST_PROPERTY = "elasticsearch.host";
    private static final String PORT_PROPERTY = "elasticsearch.port";
    private static final String INDEX_PROPERTY = "elasticsearch.index";
    private static final String USERNAME_PROPERTY = "elasticsearch.username";
    private static final String PASSWORD_PROPERTY = "elasticsearch.password";
    private static final String BATCH_SIZE_PROPERTY = "elasticsearch.batchSize";

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 9200;
    private static final String DEFAULT_INDEX = "application-logs";
    private static final int DEFAULT_BATCH_SIZE = 100;

    private ObjectMapper objectMapper;
    private String elasticsearchUrl;
    private String indexName;
    private String username;
    private String password;
    private int batchSize;

    private final List<LogEntry> batch = new ArrayList<>();
    private final AtomicLong documentId = new AtomicLong(1);
    private final DateTimeFormatter timestampFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public ElasticsearchAppender() {
        super("ElasticsearchAppender");
    }

    @Override
    public void configure(LoggerConfig config) {
        super.configure(config);

        // Get configuration properties
        String host = (String) config.getProperty(HOST_PROPERTY, DEFAULT_HOST);
        int port = ((Number) config.getProperty(PORT_PROPERTY, DEFAULT_PORT)).intValue();
        this.indexName = (String) config.getProperty(INDEX_PROPERTY, DEFAULT_INDEX);
        this.username = (String) config.getProperty(USERNAME_PROPERTY);
        this.password = (String) config.getProperty(PASSWORD_PROPERTY);
        this.batchSize = ((Number) config.getProperty(BATCH_SIZE_PROPERTY, DEFAULT_BATCH_SIZE)).intValue();

        // Build Elasticsearch URL
        this.elasticsearchUrl = String.format("http://%s:%d", host, port);

        // Initialize JSON mapper
        this.objectMapper = new ObjectMapper();

        // Create index if it doesn't exist
        createIndexIfNotExists();
    }

    private void createIndexIfNotExists() {
        try {
            // Check if index exists
            int responseCode = sendHttpRequest("HEAD", "/" + indexName, null);

            if (responseCode == 404) {
                // Index doesn't exist, create it
                String indexMapping = createIndexMapping();
                sendHttpRequest("PUT", "/" + indexName, indexMapping);
            }
        } catch (Exception e) {
            handleAsyncError(new RuntimeException("Failed to create Elasticsearch index", e));
        }
    }

    private String createIndexMapping() {
        ObjectNode mapping = objectMapper.createObjectNode();
        ObjectNode properties = objectMapper.createObjectNode();

        // Define field mappings
        ObjectNode timestamp = objectMapper.createObjectNode();
        timestamp.put("type", "date");
        timestamp.put("format", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        properties.set("timestamp", timestamp);

        ObjectNode level = objectMapper.createObjectNode();
        level.put("type", "keyword");
        properties.set("level", level);

        ObjectNode logger = objectMapper.createObjectNode();
        logger.put("type", "keyword");
        properties.set("logger", logger);

        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "text");
        properties.set("message", message);

        ObjectNode exception = objectMapper.createObjectNode();
        exception.put("type", "text");
        properties.set("exception", exception);

        ObjectNode mappings = objectMapper.createObjectNode();
        mappings.set("properties", properties);
        mapping.set("mappings", mappings);

        try {
            return objectMapper.writeValueAsString(mapping);
        } catch (Exception e) {
            return "{}";
        }
    }

    @Override
    protected void doAppend(LogEntry logEntry) {
        synchronized (batch) {
            batch.add(logEntry);

            if (batch.size() >= batchSize) {
                sendBatch();
            }
        }
    }

    private void sendBatch() {
        if (batch.isEmpty()) {
            return;
        }

        try {
            StringBuilder bulkBody = new StringBuilder();

            for (LogEntry entry : batch) {
                // Index action
                ObjectNode indexAction = objectMapper.createObjectNode();
                ObjectNode indexMeta = objectMapper.createObjectNode();
                indexMeta.put("_index", indexName);
                indexMeta.put("_id", documentId.getAndIncrement());
                indexAction.set("index", indexMeta);

                bulkBody.append(objectMapper.writeValueAsString(indexAction))
                        .append("\n");

                // Document source
                ObjectNode document = createDocument(entry);
                bulkBody.append(objectMapper.writeValueAsString(document))
                        .append("\n");
            }

            // Send bulk request
            int responseCode = sendHttpRequest("POST", "/_bulk", bulkBody.toString());

            if (responseCode >= 400) {
                handleAsyncError(new RuntimeException(
                        "Elasticsearch bulk request failed with status: " + responseCode));
            }

            batch.clear();

        } catch (Exception e) {
            handleAsyncError(e);
        }
    }

    private ObjectNode createDocument(LogEntry entry) {
        ObjectNode document = objectMapper.createObjectNode();

        document.put("timestamp", entry.getTimestamp().format(timestampFormatter));
        document.put("level", entry.getLevel().toString());
        document.put("message", entry.getMessage());

        if (entry.getLogger() != null) {
            document.put("logger", entry.getLogger());
        }

        if (entry.getException() != null) {
            document.put("exception", entry.getException().toString());
        }

        if (entry.getMetadata() != null && !entry.getMetadata().isEmpty()) {
            ObjectNode metadata = objectMapper.createObjectNode();
            entry.getMetadata().forEach((key, value) -> {
                if (value != null) {
                    metadata.put(key, value.toString());
                }
            });
            document.set("metadata", metadata);
        }

        return document;
    }

    private int sendHttpRequest(String method, String path, String body) {
        try {
            URL url = new URL(elasticsearchUrl + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set method
            connection.setRequestMethod(method);

            // Set headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Add authentication if configured
            if (username != null && password != null) {
                String auth = java.util.Base64.getEncoder()
                        .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
                connection.setRequestProperty("Authorization", "Basic " + auth);
            }

            // Set timeouts
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(30000);

            // Send body if provided
            if (body != null && ("POST".equals(method) || "PUT".equals(method))) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = body.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            return connection.getResponseCode();

        } catch (Exception e) {
            handleAsyncError(e);
            return 500; // Return error status
        }
    }

    @Override
    protected void doFlush() {
        synchronized (batch) {
            if (!batch.isEmpty()) {
                sendBatch();
            }
        }
    }

    @Override
    protected void doClose() {
        // Send any remaining logs
        doFlush();
    }
}

package com.dsantos.logger.impl;

import com.dsantos.logger.LogMessage;
import com.dsantos.logger.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ElkLogger implements Logger {

    private final String endpoint;
    private final String index;
    private final HttpClient httpClient;

    public ElkLogger(String endpoint, String index) {
        this.endpoint = endpoint;
        this.index = index;
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public void log(LogMessage message) {
        String json = """
                {"level":"%s","message":"%s","timestamp":"%s"}
                """.formatted(message.level(), message.content(), message.timestamp()).strip();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint + "/" + index + "/_doc"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send log to ELK at " + endpoint, e);
        }
    }
}


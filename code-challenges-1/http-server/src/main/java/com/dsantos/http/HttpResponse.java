package com.dsantos.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private final HttpExchange exchange;

    public HttpResponse(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public void ok(String body) throws IOException {
        send(200, body, "text/plain");
    }

    public void json(String body) throws IOException {
        send(200, body, "application/json");
    }

    public void notFound(String body) throws IOException {
        send(404, body, "text/plain");
    }

    public void badRequest(String body) throws IOException {
        send(400, body, "text/plain");
    }

    public void send(int status, String body, String contentType) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType + "; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }
}

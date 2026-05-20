package com.dsantos.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Router {
    private final Map<String, Consumer<HttpExchange>> routes = new HashMap<>();

    public void get(String path, Consumer<HttpExchange> handler) {
        routes.put("GET:" + path, handler);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String key = exchange.getRequestMethod() + ":" + exchange.getRequestURI().getPath();
        Consumer<HttpExchange> handler = routes.get(key);
        if (handler == null) {
            byte[] body = "Not Found".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(404, body.length);
            exchange.getResponseBody().write(body);
            exchange.getResponseBody().close();
            return;
        }
        handler.accept(exchange);
    }
}

package com.dsantos.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map<String, Handler> routes = new HashMap<>();

    public void get(String path, Handler handler) {
        routes.put("GET:" + path, handler);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String uri = exchange.getRequestURI().toString();
        long start = System.currentTimeMillis();

        String key = method + ":" + exchange.getRequestURI().getPath();
        Handler handler = routes.get(key);

        if (handler == null) {
            byte[] body = "Not Found".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(404, body.length);
            exchange.getResponseBody().write(body);
            exchange.getResponseBody().close();
            log(method, uri, 404, start);
            return;
        }

        HttpRequest req = new HttpRequest(exchange);
        HttpResponse res = new HttpResponse(exchange);
        try {
            handler.handle(req, res);
            log(method, uri, 200, start);
        } catch (Exception e) {
            byte[] body = "Internal Server Error".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(500, body.length);
            exchange.getResponseBody().write(body);
            exchange.getResponseBody().close();
            log(method, uri, 500, start);
        }
    }

    private void log(String method, String uri, int status, long start) {
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("[%s] %s %s -> %d (%dms)%n", Instant.now(), method, uri, status, elapsed);
    }
}

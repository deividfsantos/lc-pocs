package com.dsantos.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map<String, Handler> routes = new HashMap<>();

    public void get(String path, Handler handler) {
        routes.put("GET:" + path, handler);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String key = exchange.getRequestMethod() + ":" + exchange.getRequestURI().getPath();
        Handler handler = routes.get(key);
        if (handler == null) {
            byte[] body = "Not Found".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(404, body.length);
            exchange.getResponseBody().write(body);
            exchange.getResponseBody().close();
            return;
        }
        HttpRequest req = new HttpRequest(exchange);
        HttpResponse res = new HttpResponse(exchange);
        try {
            handler.handle(req, res);
        } catch (Exception e) {
            byte[] body = "Internal Server Error".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(500, body.length);
            exchange.getResponseBody().write(body);
            exchange.getResponseBody().close();
        }
    }
}

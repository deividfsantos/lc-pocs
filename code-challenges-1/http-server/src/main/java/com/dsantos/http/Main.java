package com.dsantos.http;

import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router();
        router.get("/hello", exchange -> {
            try {
                byte[] body = "Hello, World!".getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, body.length);
                exchange.getResponseBody().write(body);
                exchange.getResponseBody().close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Server server = new Server(8080, router);
        server.start();
    }
}

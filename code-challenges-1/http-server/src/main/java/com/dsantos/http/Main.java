package com.dsantos.http;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.get("/hello", exchange -> {
            try {
                byte[] body = "Hello, World!".getBytes();
                exchange.sendResponseHeaders(200, body.length);
                exchange.getResponseBody().write(body);
                exchange.getResponseBody().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        server.start();
    }
}

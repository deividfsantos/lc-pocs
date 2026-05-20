package com.dsantos.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Server {
    private final int port;
    private final Map<String, Consumer<HttpExchange>> handlers = new HashMap<>();

    public Server(int port) {
        this.port = port;
    }

    public void get(String path, Consumer<HttpExchange> handler) {
        handlers.put(path, handler);
    }

    public void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            Consumer<HttpExchange> handler = handlers.get(path);
            if (handler != null) {
                handler.accept(exchange);
            } else {
                exchange.sendResponseHeaders(404, 0);
                exchange.getResponseBody().close();
            }
        });
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
        System.out.println("Server running on port " + port);
    }
}

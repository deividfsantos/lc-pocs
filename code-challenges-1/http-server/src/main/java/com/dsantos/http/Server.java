package com.dsantos.http;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    private final ServerConfig config;
    private final Router router;

    public Server(int port, Router router) {
        this(ServerConfig.onPort(port), router);
    }

    public Server(ServerConfig config, Router router) {
        this.config = config;
        this.router = router;
    }

    public void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(config.port()), config.backlog());
        server.createContext("/", router::handle);
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
        System.out.println("Server started on port " + config.port());
    }
}

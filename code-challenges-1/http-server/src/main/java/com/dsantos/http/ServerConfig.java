package com.dsantos.http;

public record ServerConfig(int port, int backlog) {
    public static ServerConfig defaults() {
        return new ServerConfig(8080, 0);
    }

    public static ServerConfig onPort(int port) {
        return new ServerConfig(port, 0);
    }
}

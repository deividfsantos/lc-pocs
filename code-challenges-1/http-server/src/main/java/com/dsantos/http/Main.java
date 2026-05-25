package com.dsantos.http;

import com.dsantos.http.handlers.UsersHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router()
            .get("/health", (req, res) -> res.json("{\"status\":\"up\"}"))
            .get("/status", (req, res) -> res.json("{\"version\":\"1.0\",\"uptime\":\"running\"}"))
            .get("/hello", (req, res) -> res.ok("Hello, World!"))
            .get("/users", new UsersHandler());

        Server server = new Server(8080, router);
        server.start();
    }
}

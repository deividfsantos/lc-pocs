package com.dsantos.http;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router();
        router.get("/hello", (req, res) -> {
            res.ok("Hello, World!");
        });

        Server server = new Server(8080, router);
        server.start();
    }
}

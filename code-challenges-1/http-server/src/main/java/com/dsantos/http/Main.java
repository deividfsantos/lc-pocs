package com.dsantos.http;

import com.dsantos.http.handlers.ProductsHandler;
import com.dsantos.http.handlers.UsersHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router();
        router.get("/hello", (req, res) -> res.ok("Hello, World!"));
        router.get("/users", new UsersHandler());
        router.get("/products", new ProductsHandler());

        Server server = new Server(8080, router);
        server.start();
    }
}

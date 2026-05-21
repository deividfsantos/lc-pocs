package com.dsantos.http;

import com.sun.net.httpserver.HttpExchange;

public class HttpRequest {
    private final HttpExchange exchange;

    public HttpRequest(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public String getPath() {
        return exchange.getRequestURI().getPath();
    }

    public String getMethod() {
        return exchange.getRequestMethod();
    }

    public String getHeader(String name) {
        return exchange.getRequestHeaders().getFirst(name);
    }
}

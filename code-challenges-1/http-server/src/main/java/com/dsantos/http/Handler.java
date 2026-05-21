package com.dsantos.http;

@FunctionalInterface
public interface Handler {
    void handle(HttpRequest req, HttpResponse res) throws Exception;
}

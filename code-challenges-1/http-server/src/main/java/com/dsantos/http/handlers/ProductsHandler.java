package com.dsantos.http.handlers;

import com.dsantos.http.Handler;
import com.dsantos.http.HttpRequest;
import com.dsantos.http.HttpResponse;

public class ProductsHandler implements Handler {
    @Override
    public void handle(HttpRequest req, HttpResponse res) throws Exception {
        res.json("{\"products\":[\"laptop\",\"mouse\",\"keyboard\"]}");
    }
}

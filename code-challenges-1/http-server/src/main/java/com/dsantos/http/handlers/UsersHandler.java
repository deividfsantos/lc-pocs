package com.dsantos.http.handlers;

import com.dsantos.http.Handler;
import com.dsantos.http.HttpRequest;
import com.dsantos.http.HttpResponse;
import com.dsantos.http.Json;
import java.util.List;
import java.util.Map;

public class UsersHandler implements Handler {
    private final List<String> users = List.of("alice", "bob", "charlie");

    @Override
    public void handle(HttpRequest req, HttpResponse res) throws Exception {
        String name = req.getQueryParam("name");
        if (name != null) {
            boolean found = users.contains(name);
            res.json(Json.of(Map.of("name", name, "found", found)));
        } else {
            res.json(Json.array("users", users));
        }
    }
}

package com.dsantos.http.handlers;

import com.dsantos.http.Handler;
import com.dsantos.http.HttpRequest;
import com.dsantos.http.HttpResponse;
import java.util.List;

public class UsersHandler implements Handler {
    private final List<String> users = List.of("alice", "bob", "charlie");

    @Override
    public void handle(HttpRequest req, HttpResponse res) throws Exception {
        String name = req.getQueryParam("name");
        if (name != null) {
            boolean found = users.contains(name);
            res.json("{\"name\":\"" + name + "\",\"found\":" + found + "}");
        } else {
            res.json("{\"users\":[\"alice\",\"bob\",\"charlie\"]}");
        }
    }
}

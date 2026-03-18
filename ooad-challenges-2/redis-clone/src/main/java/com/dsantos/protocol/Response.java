package com.dsantos.protocol;

import java.util.List;

public final class Response {

    private final String raw;

    private Response(String raw) {
        this.raw = raw;
    }

    public static Response ok() {
        return new Response("+OK\r\n");
    }

    public static Response error(String message) {
        return new Response("-ERR " + message + "\r\n");
    }

    public static Response nil() {
        return new Response("$-1\r\n");
    }

    public static Response bulk(String value) {
        return new Response("$" + value.length() + "\r\n" + value + "\r\n");
    }

    public static Response integer(long value) {
        return new Response(":" + value + "\r\n");
    }

    public static Response array(List<String> items) {
        StringBuilder sb = new StringBuilder("*").append(items.size()).append("\r\n");
        for (String item : items) {
            sb.append("$").append(item.length()).append("\r\n").append(item).append("\r\n");
        }
        return new Response(sb.toString());
    }

    @Override
    public String toString() {
        return raw;
    }
}


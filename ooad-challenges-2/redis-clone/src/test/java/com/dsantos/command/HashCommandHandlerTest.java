package com.dsantos.command;

import com.dsantos.protocol.CommandParser;
import com.dsantos.protocol.Response;
import com.dsantos.store.HashStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HashCommandHandlerTest {

    private HashCommandHandler handler;
    private final CommandParser parser = new CommandParser();

    @BeforeEach
    void setUp() {
        handler = new HashCommandHandler(new HashStore());
    }

    @Test
    void hsetReturnsOk() {
        assertEquals(Response.ok().toString(), handle("HSET user name Alice").toString());
    }

    @Test
    void hsetWithMissingArgsReturnsError() {
        assertTrue(handle("HSET user name").toString().startsWith("-ERR"));
    }

    @Test
    void hgetReturnsStoredField() {
        handle("HSET user name Alice");
        assertEquals(Response.bulk("Alice").toString(), handle("HGET user name").toString());
    }

    @Test
    void hgetMissingFieldReturnsNil() {
        handle("HSET user name Alice");
        assertEquals(Response.nil().toString(), handle("HGET user missing").toString());
    }

    @Test
    void hgetMissingHashReturnsNil() {
        assertEquals(Response.nil().toString(), handle("HGET missing field").toString());
    }

    @Test
    void hgetWithMissingArgsReturnsError() {
        assertTrue(handle("HGET user").toString().startsWith("-ERR"));
    }

    @Test
    void hkeysReturnsAllFields() {
        handle("HSET user name Alice");
        handle("HSET user age 30");
        String result = handle("HKEYS user").toString();
        assertTrue(result.contains("name"));
        assertTrue(result.contains("age"));
    }

    @Test
    void hkeysOnMissingHashReturnsEmptyArray() {
        assertEquals(Response.array(List.of()).toString(), handle("HKEYS missing").toString());
    }

    @Test
    void hvalsReturnsAllValues() {
        handle("HSET user name Alice");
        handle("HSET user age 30");
        String result = handle("HVALS user").toString();
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("30"));
    }

    @Test
    void hvalsOnMissingHashReturnsEmptyArray() {
        assertEquals(Response.array(List.of()).toString(), handle("HVALS missing").toString());
    }

    private Response handle(String line) {
        return handler.handle(parser.parse(line));
    }
}


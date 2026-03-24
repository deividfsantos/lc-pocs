package com.dsantos.command;
import com.dsantos.protocol.CommandParser;
import com.dsantos.protocol.Response;
import com.dsantos.store.StringStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class StringCommandHandlerTest {
    private StringCommandHandler handler;
    private final CommandParser parser = new CommandParser();
    @BeforeEach
    void setUp() {
        handler = new StringCommandHandler(new StringStore());
    }
    @Test
    void setReturnsOk() {
        assertEquals(Response.ok().toString(), handle("SET foo bar").toString());
    }
    @Test
    void setWithMissingArgsReturnsError() {
        assertTrue(handle("SET foo").toString().startsWith("-ERR"));
    }
    @Test
    void getReturnsStoredValue() {
        handle("SET city Rome");
        assertEquals(Response.bulk("Rome").toString(), handle("GET city").toString());
    }
    @Test
    void getMissingKeyReturnsNil() {
        assertEquals(Response.nil().toString(), handle("GET missing").toString());
    }
    @Test
    void getWithMissingArgsReturnsError() {
        assertTrue(handle("GET").toString().startsWith("-ERR"));
    }
    @Test
    void delExistingKeyReturnsOne() {
        handle("SET key value");
        assertEquals(Response.integer(1).toString(), handle("DEL key").toString());
    }
    @Test
    void delMissingKeyReturnsZero() {
        assertEquals(Response.integer(0).toString(), handle("DEL missing").toString());
    }
    @Test
    void appendToNewKeyReturnsLength() {
        assertEquals(Response.integer(5).toString(), handle("APPEND key hello").toString());
    }
    @Test
    void appendToExistingKeyConcatenatesAndReturnsLength() {
        handle("SET key hello");
        assertEquals(Response.integer(11).toString(), handle("APPEND key -world").toString());
    }
    @Test
    void appendWithMissingArgsReturnsError() {
        assertTrue(handle("APPEND key").toString().startsWith("-ERR"));
    }
    private Response handle(String line) {
        return handler.handle(parser.parse(line));
    }
}

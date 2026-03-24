package com.dsantos.command;
import com.dsantos.protocol.CommandParser;
import com.dsantos.protocol.Response;
import com.dsantos.store.HashStore;
import com.dsantos.store.StringStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CommandDispatcherTest {
    private CommandDispatcher dispatcher;
    private final CommandParser parser = new CommandParser();
    @BeforeEach
    void setUp() {
        dispatcher = new CommandDispatcher();
        dispatcher.register(new StringCommandHandler(new StringStore()));
        dispatcher.register(new HashCommandHandler(new HashStore()));
    }
    @Test
    void routesStringCommand() {
        assertEquals(Response.ok().toString(), dispatch("SET key value").toString());
    }
    @Test
    void routesHashCommand() {
        assertEquals(Response.ok().toString(), dispatch("HSET hash field value").toString());
    }
    @Test
    void unknownCommandReturnsError() {
        assertTrue(dispatch("UNKNOWN arg").toString().startsWith("-ERR"));
    }
    @Test
    void commandNamesAreCaseInsensitive() {
        assertEquals(Response.ok().toString(), dispatch("set key value").toString());
    }
    private Response dispatch(String line) {
        return dispatcher.dispatch(parser.parse(line));
    }
}

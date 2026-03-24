package com.dsantos.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    private final CommandParser parser = new CommandParser();

    @Test
    void parsesCommandNameAsUpperCase() {
        RawCommand cmd = parser.parse("set foo bar");
        assertEquals("SET", cmd.name());
    }

    @Test
    void parsesArguments() {
        RawCommand cmd = parser.parse("SET mykey myvalue");
        assertEquals(2, cmd.argCount());
        assertEquals("mykey", cmd.arg(0));
        assertEquals("myvalue", cmd.arg(1));
    }

    @Test
    void parsesCommandWithNoArguments() {
        RawCommand cmd = parser.parse("PING");
        assertEquals("PING", cmd.name());
        assertEquals(0, cmd.argCount());
    }

    @Test
    void handlesExtraWhitespace() {
        RawCommand cmd = parser.parse("  SET   foo   bar  ");
        assertEquals("SET", cmd.name());
        assertEquals("foo", cmd.arg(0));
        assertEquals("bar", cmd.arg(1));
    }
}


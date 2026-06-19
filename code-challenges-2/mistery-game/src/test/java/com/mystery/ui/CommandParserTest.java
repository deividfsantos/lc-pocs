package com.mystery.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {
    private final CommandParser parser = new CommandParser();

    @Test
    void parsesCommandWithArgument() {
        CommandParser.ParsedCommand result = parser.parse("examine suite");
        assertEquals("examine", result.command());
        assertEquals("suite", result.argument());
    }

    @Test
    void parsesCommandWithoutArgument() {
        CommandParser.ParsedCommand result = parser.parse("suspects");
        assertEquals("suspects", result.command());
        assertEquals("", result.argument());
    }

    @Test
    void lowercasesInput() {
        CommandParser.ParsedCommand result = parser.parse("EXAMINE SUITE");
        assertEquals("examine", result.command());
        assertEquals("suite", result.argument());
    }

    @Test
    void trimsWhitespace() {
        CommandParser.ParsedCommand result = parser.parse("  talk  diana  ");
        assertEquals("talk", result.command());
        assertEquals("diana", result.argument());
    }
}

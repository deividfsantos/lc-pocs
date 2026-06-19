package com.mystery.ui;

public class CommandParser {
    public record ParsedCommand(String command, String argument) {}

    public ParsedCommand parse(String input) {
        String[] parts = input.trim().toLowerCase().split(" ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1].trim() : "";
        return new ParsedCommand(command, argument);
    }
}

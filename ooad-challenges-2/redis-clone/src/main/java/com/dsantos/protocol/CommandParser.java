package com.dsantos.protocol;

import java.util.Arrays;
import java.util.List;

public class CommandParser {

    public RawCommand parse(String line) {
        String[] parts = line.trim().split("\\s+");
        String name = parts[0].toUpperCase();
        List<String> args = List.copyOf(Arrays.asList(parts).subList(1, parts.length));
        return new RawCommand(name, args);
    }
}


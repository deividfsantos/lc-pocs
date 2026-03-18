package com.dsantos.protocol;

import java.util.List;

public record RawCommand(String name, List<String> args) {

    public String arg(int index) {
        return args.get(index);
    }

    public int argCount() {
        return args.size();
    }
}


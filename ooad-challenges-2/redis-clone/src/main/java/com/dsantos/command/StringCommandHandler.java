package com.dsantos.command;

import com.dsantos.protocol.RawCommand;
import com.dsantos.protocol.Response;
import com.dsantos.store.StringStore;

import java.util.Set;

public class StringCommandHandler implements CommandHandler {

    private final StringStore store;

    public StringCommandHandler(StringStore store) {
        this.store = store;
    }

    @Override
    public Set<String> supportedCommands() {
        return Set.of("SET", "GET", "DEL", "APPEND");
    }

    @Override
    public Response handle(RawCommand command) {
        return switch (command.name()) {
            case "SET"    -> handleSet(command);
            case "GET"    -> handleGet(command);
            case "DEL"    -> handleDel(command);
            case "APPEND" -> handleAppend(command);
            default       -> Response.error("unknown command '" + command.name() + "'");
        };
    }

    private Response handleSet(RawCommand cmd) {
        if (cmd.argCount() < 2) return Response.error("wrong number of arguments for 'set'");
        store.set(cmd.arg(0), cmd.arg(1));
        return Response.ok();
    }

    private Response handleGet(RawCommand cmd) {
        if (cmd.argCount() < 1) return Response.error("wrong number of arguments for 'get'");
        return store.get(cmd.arg(0)).map(Response::bulk).orElse(Response.nil());
    }

    private Response handleDel(RawCommand cmd) {
        if (cmd.argCount() < 1) return Response.error("wrong number of arguments for 'del'");
        return Response.integer(store.remove(cmd.arg(0)) ? 1 : 0);
    }

    private Response handleAppend(RawCommand cmd) {
        if (cmd.argCount() < 2) return Response.error("wrong number of arguments for 'append'");
        return Response.integer(store.append(cmd.arg(0), cmd.arg(1)));
    }
}


package com.dsantos.command;

import com.dsantos.protocol.RawCommand;
import com.dsantos.protocol.Response;
import com.dsantos.store.HashStore;

import java.util.Set;

public class HashCommandHandler implements CommandHandler {

    private final HashStore store;

    public HashCommandHandler(HashStore store) {
        this.store = store;
    }

    @Override
    public Set<String> supportedCommands() {
        return Set.of("HSET", "HGET", "HKEYS", "HVALS");
    }

    @Override
    public Response handle(RawCommand command) {
        return switch (command.name()) {
            case "HSET"  -> handleHSet(command);
            case "HGET"  -> handleHGet(command);
            case "HKEYS" -> handleHKeys(command);
            case "HVALS" -> handleHVals(command);
            default      -> Response.error("unknown command '" + command.name() + "'");
        };
    }

    private Response handleHSet(RawCommand cmd) {
        if (cmd.argCount() < 3) return Response.error("wrong number of arguments for 'hset'");
        store.set(cmd.arg(0), cmd.arg(1), cmd.arg(2));
        return Response.ok();
    }

    private Response handleHGet(RawCommand cmd) {
        if (cmd.argCount() < 2) return Response.error("wrong number of arguments for 'hget'");
        return store.get(cmd.arg(0), cmd.arg(1)).map(Response::bulk).orElse(Response.nil());
    }

    private Response handleHKeys(RawCommand cmd) {
        if (cmd.argCount() < 1) return Response.error("wrong number of arguments for 'hkeys'");
        return Response.array(store.keys(cmd.arg(0)));
    }

    private Response handleHVals(RawCommand cmd) {
        if (cmd.argCount() < 1) return Response.error("wrong number of arguments for 'hvals'");
        return Response.array(store.values(cmd.arg(0)));
    }
}


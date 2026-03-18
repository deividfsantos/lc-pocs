package com.dsantos.command;

import com.dsantos.protocol.RawCommand;
import com.dsantos.protocol.Response;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher {

    private final Map<String, CommandHandler> handlers = new HashMap<>();

    public void register(CommandHandler handler) {
        for (String command : handler.supportedCommands()) {
            handlers.put(command, handler);
        }
    }

    public Response dispatch(RawCommand command) {
        CommandHandler handler = handlers.get(command.name());
        if (handler == null) {
            return Response.error("unknown command '" + command.name() + "'");
        }
        return handler.handle(command);
    }
}



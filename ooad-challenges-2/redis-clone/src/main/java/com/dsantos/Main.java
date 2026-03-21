package com.dsantos;

import com.dsantos.command.CommandDispatcher;
import com.dsantos.command.HashCommandHandler;
import com.dsantos.command.StringCommandHandler;
import com.dsantos.server.Server;
import com.dsantos.store.HashStore;
import com.dsantos.store.StringStore;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.register(new StringCommandHandler(new StringStore()));
        dispatcher.register(new HashCommandHandler(new HashStore()));
        new Server(6379, dispatcher).start();
    }
}

package com.dsantos;

import com.dsantos.command.CommandDispatcher;
import com.dsantos.server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandDispatcher dispatcher = new CommandDispatcher();
        new Server(6379, dispatcher).start();
    }
}

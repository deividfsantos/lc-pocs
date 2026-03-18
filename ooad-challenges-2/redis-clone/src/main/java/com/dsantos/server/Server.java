package com.dsantos.server;

import com.dsantos.command.CommandDispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int port;
    private final CommandDispatcher dispatcher;

    public Server(int port, CommandDispatcher dispatcher) {
        this.port = port;
        this.dispatcher = dispatcher;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on port " + port);
            while (true) {
                Socket client = serverSocket.accept();
                new Thread(new ClientHandler(client, dispatcher)).start();
            }
        }
    }
}


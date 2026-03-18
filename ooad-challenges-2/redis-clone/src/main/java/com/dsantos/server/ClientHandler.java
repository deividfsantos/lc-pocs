package com.dsantos.server;

import com.dsantos.command.CommandDispatcher;
import com.dsantos.protocol.CommandParser;
import com.dsantos.protocol.RawCommand;
import com.dsantos.protocol.Response;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final CommandDispatcher dispatcher;
    private final CommandParser parser = new CommandParser();

    public ClientHandler(Socket socket, CommandDispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                RawCommand command = parser.parse(line);
                Response response = dispatcher.dispatch(command);
                writer.print(response);
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        }
    }
}


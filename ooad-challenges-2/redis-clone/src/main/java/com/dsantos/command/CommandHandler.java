package com.dsantos.command;

import com.dsantos.protocol.RawCommand;
import com.dsantos.protocol.Response;

import java.util.Set;

public interface CommandHandler {

    Set<String> supportedCommands();

    Response handle(RawCommand command);
}


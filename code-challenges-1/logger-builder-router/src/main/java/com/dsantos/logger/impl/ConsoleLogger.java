package com.dsantos.logger.impl;

import com.dsantos.logger.LogMessage;
import com.dsantos.logger.Logger;

public class ConsoleLogger implements Logger {

    @Override
    public void log(LogMessage message) {
        System.out.printf("[%s] [%s] %s%n",
                message.timestamp(),
                message.level(),
                message.content());
    }
}


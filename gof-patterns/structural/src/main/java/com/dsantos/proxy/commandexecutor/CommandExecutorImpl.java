package com.dsantos.proxy.commandexecutor;


import java.io.IOException;

public class CommandExecutorImpl implements CommandExecutor {


    @Override
    public void runCommand(String cmd) throws IOException {
        Runtime.getRuntime().exec(cmd);
        System.out.println("'" + cmd + "' command executed.");
    }

}
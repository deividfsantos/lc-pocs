package com.dsantos.proxy.databasequery.proxy.commandexecutor;


public interface CommandExecutor {

    public void runCommand(String cmd) throws Exception;
}
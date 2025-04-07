package com.dsantos.proxy.commandexecutor;


public class CommandExecutorProxy implements CommandExecutor {

    private boolean isAdmin;
    private final CommandExecutor executor;

    public CommandExecutorProxy(String user, String pwd) {
        if ("dsantos".equals(user) && "password".equals(pwd)) isAdmin = true;
        this.executor = new CommandExecutorImpl();
    }

    @Override
    public void runCommand(String cmd) throws Exception {
        if (isAdmin) {
            executor.runCommand(cmd);
        } else {
            if (cmd.trim().startsWith("rm")) {
                throw new Exception("rm command is not allowed for non-admin users.");
            } else {
                executor.runCommand(cmd);
            }
        }
    }

}
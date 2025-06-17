package com.dsantos.command;

public interface FileSystemReceiver {

    void openFile();

    void writeFile();

    void closeFile();
}
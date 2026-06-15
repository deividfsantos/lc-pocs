package com.mystery.ui;

import java.util.Scanner;

public class TerminalUI {
    private final Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.println(message);
    }

    public void printBlank() {
        System.out.println();
    }

    public String readCommand() {
        System.out.print("> ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public void printSeparator() {
        System.out.println("─".repeat(60));
    }
}

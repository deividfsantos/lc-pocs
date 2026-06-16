package com.mystery;

import com.mystery.ui.TerminalUI;

public class Main {
    public static void main(String[] args) {
        TerminalUI ui = new TerminalUI();
        GameEngine engine = new GameEngine();

        ui.printSeparator();
        ui.print("  MURDER AT THE RIVERSIDE HOTEL");
        ui.print("  A Mystery Game");
        ui.printSeparator();
        ui.print("Victor Ashwood was found dead in suite 501 at midnight.");
        ui.print("You have been called to investigate.");
        ui.printBlank();
        ui.print("Type 'help' for a list of commands.");
        ui.printSeparator();

        boolean running = true;
        while (running) {
            String input = ui.readCommand();
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "help" -> ui.print("""
                    Commands:
                      examine [location]  - search a location for clues
                      talk [name]         - talk to a suspect
                      suspects            - list persons of interest
                      locations           - list searchable locations
                      clues               - show clues you have found
                      accuse [name]       - accuse someone of the murder
                      quit                - leave the investigation""");
                case "examine" -> ui.print(engine.examine(argument));
                case "talk" -> ui.print(engine.talk(argument));
                case "suspects" -> ui.print(engine.listSuspects());
                case "locations" -> ui.print(engine.listLocations());
                case "clues" -> ui.print(engine.listFoundClues());
                case "accuse" -> ui.print("Not yet implemented. Gather more evidence first.");
                case "quit", "exit" -> {
                    ui.print("Leaving the investigation.");
                    running = false;
                }
                default -> ui.print("Unknown command '" + command + "'. Type 'help' for available commands.");
            }
        }
    }
}

package com.dsantos;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String STORAGE_FILE = "notes.txt";

    static void main() {
        NoteManager manager = new NoteManager(STORAGE_FILE);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Note Taking System ===");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Add note\n2. Edit note\n3. Delete note\n4. Save notes\n5. Sync notes\n6. List notes\n0. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Content: ");
                    String content = scanner.nextLine();
                    Note note = manager.addNote(title, content);
                    System.out.println("Added: " + note);
                }
                case "2" -> {
                    System.out.print("Note ID: ");
                    String id = scanner.nextLine();
                    System.out.print("New title: ");
                    String title = scanner.nextLine();
                    System.out.print("New content: ");
                    String content = scanner.nextLine();
                    System.out.println(manager.editNote(id, title, content) ? "Updated." : "Note not found.");
                }
                case "3" -> {
                    System.out.print("Note ID: ");
                    String id = scanner.nextLine();
                    System.out.println(manager.deleteNote(id) ? "Deleted." : "Note not found.");
                }
                case "4" -> {
                    try {
                        manager.saveNotes();
                        System.out.println("Notes saved to " + STORAGE_FILE);
                    } catch (Exception e) {
                        System.out.println("Save failed: " + e.getMessage());
                    }
                }
                case "5" -> {
                    try {
                        manager.sync();
                        System.out.println("Notes synced from " + STORAGE_FILE);
                    } catch (Exception e) {
                        System.out.println("Sync failed: " + e.getMessage());
                    }
                }
                case "6" -> {
                    List<Note> notes = manager.getAllNotes();
                    if (notes.isEmpty()) {
                        System.out.println("No notes.");
                    } else {
                        notes.forEach(System.out::println);
                    }
                }
                case "0" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }
}

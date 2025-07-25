package com.dsantos.memento;

public class Application {

    public static void main(String[] args) {
        Editor editor = new Editor();
        History history = new History();

        editor.setContent("First line.");
        history.save(editor.save());
        System.out.println("Current content: " + editor.getContent());

        editor.setContent("Second line.");
        history.save(editor.save());
        System.out.println("Current content: " + editor.getContent());

        editor.setContent("Third line.");
        System.out.println("Current content: " + editor.getContent());

        Memento lastSavedState = history.undo();
        if (lastSavedState != null) {
            editor.restore(lastSavedState);
        }
        System.out.println("After undo: " + editor.getContent());

        lastSavedState = history.undo();
        if (lastSavedState != null) {
            editor.restore(lastSavedState);
        }
        System.out.println("After the second undo: " + editor.getContent());
    }
}
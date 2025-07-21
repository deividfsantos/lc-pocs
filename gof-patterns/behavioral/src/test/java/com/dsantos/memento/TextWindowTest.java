package com.dsantos.memento;

import org.junit.jupiter.api.Test;

class TextWindowTest {

    @Test
    void addText() {
        TextEditor textEditor = new TextEditor(new TextWindow());
        textEditor.write("The Memento Design Pattern\n");
        textEditor.write("How to implement it in Java?\n");
        textEditor.hitSave();

        textEditor.write("Buy milk and eggs before coming home\n");

        textEditor.hitUndo();

        assertThat(textEditor.print()).isEqualTo("The Memento Design Pattern\nHow to implement it in Java?\n");
    }
}
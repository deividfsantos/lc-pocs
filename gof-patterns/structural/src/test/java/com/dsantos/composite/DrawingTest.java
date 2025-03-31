package com.dsantos.composite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrawingTest {

    private Drawing drawing;
    private Shape triangle;
    private Shape circle;

    @BeforeEach
    public void setUp() {
        drawing = new Drawing();
        triangle = new Triangle();
        circle = new Circle();
    }

    @Test
    public void testAddShape() {
        drawing.add(triangle);
        drawing.add(circle);
        assertEquals(2, drawing.getShapes().size());
    }

    @Test
    public void testRemoveShape() {
        drawing.add(triangle);
        drawing.add(circle);
        drawing.remove(triangle);
        assertEquals(1, drawing.getShapes().size());
    }

    @Test
    public void testClearShapes() {
        drawing.add(triangle);
        drawing.add(circle);
        drawing.clear();
        assertTrue(drawing.getShapes().isEmpty());
    }


    @Test
    public void testDraw() {
        drawing.add(triangle);
        drawing.add(circle);
        String result = drawing.draw("Red");
        String expectedOutput = "Drawing Triangle with color Red\nDrawing Circle with color Red\n";
        assertEquals(expectedOutput, result);
    }
}
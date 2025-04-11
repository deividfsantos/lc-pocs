package com.dsantos.flyweight;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeFactoryTest {

    @Test
    void testShapeFactoryReusesInstances() {
        Shape circle1 = ShapeFactory.getShape("Circle");
        Shape circle2 = ShapeFactory.getShape("Circle");
        Shape square1 = ShapeFactory.getShape("Square");
        Shape square2 = ShapeFactory.getShape("Square");

        // Verify that the same instances are reused
        assertSame(circle1, circle2, "Circle instances should be the same");
        assertSame(square1, square2, "Square instances should be the same");
        assertNotSame(circle1, square1, "Circle and Square instances should not be the same");
    }

    @Test
    void testDrawMethodOutput() {
        Shape circle = ShapeFactory.getShape("Circle");
        Shape square = ShapeFactory.getShape("Square");

        // Verify the output of the draw method
        assertEquals("Drawing a Circle with color Red", circle.draw("Red"));
        assertEquals("Drawing a Circle with color Blue", circle.draw("Blue"));
        assertEquals("Drawing a Square with color Red", square.draw("Red"));
        assertEquals("Drawing a Square with color Blue", square.draw("Blue"));
    }
}
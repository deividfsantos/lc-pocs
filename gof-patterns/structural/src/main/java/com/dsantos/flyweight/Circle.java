package com.dsantos.flyweight;

public class Circle implements Shape {
    private final String shapeType = "Circle";

    @Override
    public String draw(String color) {
        return "Drawing a " + shapeType + " with color " + color;
    }
}

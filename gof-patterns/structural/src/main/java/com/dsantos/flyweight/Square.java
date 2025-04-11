package com.dsantos.flyweight;

public class Square implements Shape {
    private final String shapeType = "Square";

    @Override
    public String draw(String color) {
        return "Drawing a " + shapeType + " with color " + color;
    }
}
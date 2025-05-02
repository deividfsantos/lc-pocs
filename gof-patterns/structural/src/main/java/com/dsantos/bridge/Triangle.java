package com.dsantos.bridge;

public class Triangle extends Shape {

    public Triangle(Color c) {
        super(c);
    }

    @Override
    public String applyColor() {
        return "Triangle filled with color " + color.applyColor();
    }

}
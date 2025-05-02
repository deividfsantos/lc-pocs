package com.dsantos.bridge;

public class Pentagon extends Shape {

    public Pentagon(Color c) {
        super(c);
    }

    @Override
    public String applyColor() {
        return "Pentagon filled with color " + color.applyColor();
    }

}
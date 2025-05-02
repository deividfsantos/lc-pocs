package com.dsantos.bridge;

public abstract class Shape {
    protected Color color;

    public Shape(Color c) {
        this.color = c;
    }

    abstract public String applyColor();
}
package com.dsantos.composite;

public class Triangle implements Shape {

    @Override
    public String draw(String fillColor) {
        return "Drawing Triangle with color " + fillColor;
    }

}
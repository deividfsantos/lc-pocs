package com.dsantos.composite;


public class Circle implements Shape {

    @Override
    public String draw(String fillColor) {
        return "Drawing Circle with color " + fillColor;
    }

}
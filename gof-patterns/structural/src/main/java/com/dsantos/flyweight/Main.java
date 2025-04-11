package com.dsantos.flyweight;

public class Main {
    public static void main(String[] args) {
        Shape circle1 = ShapeFactory.getShape("Circle");
        System.out.println(circle1.draw("Red"));

        Shape square1 = ShapeFactory.getShape("Square");
        System.out.println(square1.draw("Red"));

        Shape square2 = ShapeFactory.getShape("Square");
        System.out.println(square2.draw("Blue"));
        
        Shape circle2 = ShapeFactory.getShape("Circle");
        System.out.println(circle2.draw("Blue"));
        
        Shape circle3 = ShapeFactory.getShape("Circle");
        System.out.println(circle3.draw("Green"));

        System.out.println("circle1 == circle2: " + (circle1 == circle2));
        System.out.println("square1 == square2: " + (square1 == square2));
        System.out.println("circle1 == square1: " + (circle1 == square1));
    }
}

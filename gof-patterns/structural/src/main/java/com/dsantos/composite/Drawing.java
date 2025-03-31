package com.dsantos.composite;


import java.util.ArrayList;
import java.util.List;

public class Drawing implements Shape {

    private final List<Shape> shapes = new ArrayList<>();

    public List<Shape> getShapes() {
        return shapes;
    }

    @Override
    public String draw(String fillColor) {
        StringBuilder sb = new StringBuilder();
        for (Shape sh : shapes) {
            sb.append(sh.draw(fillColor)).append("\n");
        }
        return sb.toString();
    }

    public void add(Shape s) {
        this.shapes.add(s);
    }

    public void remove(Shape s) {
        shapes.remove(s);
    }

    public void clear() {
        System.out.println("Clearing all the shapes from drawing");
        this.shapes.clear();
    }
}
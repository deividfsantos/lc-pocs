package com.dsantos.flyweight;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    private static final Map<String, Shape> shapes = new HashMap<>();

    public static Shape getShape(String shapeType) {
        Shape shape = shapes.get(shapeType);

        if (shape == null) {
            if ("Circle".equalsIgnoreCase(shapeType)) {
                shape = new Circle();
            } else if ("Square".equalsIgnoreCase(shapeType)) {
                shape = new Square();
            } else {
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
            }

            shapes.put(shapeType, shape);
        }

        return shape;
    }
}

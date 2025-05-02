package com.dsantos.bridge;

public class BridgePatternMain {

    public static void main(String[] args) {
        Shape tri = new Triangle(new RedColor());
        System.out.println(tri.applyColor());

        Shape pent = new Pentagon(new GreenColor());
        System.out.println(pent.applyColor());
    }
}
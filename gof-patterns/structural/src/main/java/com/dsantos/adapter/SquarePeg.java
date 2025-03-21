package com.dsantos.adapter;

public record SquarePeg(double width) {

    public double getSquare() {
        return Math.pow(this.width, 2);
    }
}
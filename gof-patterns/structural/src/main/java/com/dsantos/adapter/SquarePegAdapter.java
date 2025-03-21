package com.dsantos.adapter;

public class SquarePegAdapter extends RoundPeg {
    private final SquarePeg peg;

    public SquarePegAdapter(SquarePeg peg) {
        this.peg = peg;
    }

    @Override
    public double getRadius() {
        return (Math.sqrt(Math.pow((peg.width() / 2), 2) * 2));
    }
}
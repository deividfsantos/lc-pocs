package com.dsantos.decorator;

public class LuxuryCar extends CarDecorator {

    public LuxuryCar(Car c) {
        super(c);
    }

    @Override
    public String assemble() {
        return super.assemble() + " Adding features of Luxury Car.";
    }
}
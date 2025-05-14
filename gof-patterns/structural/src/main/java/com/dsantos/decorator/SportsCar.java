package com.dsantos.decorator;

public class SportsCar extends CarDecorator {

    public SportsCar(Car c) {
        super(c);
    }

    @Override
    public String assemble() {
        return super.assemble() + " Adding features of Sports Car.";
    }
}
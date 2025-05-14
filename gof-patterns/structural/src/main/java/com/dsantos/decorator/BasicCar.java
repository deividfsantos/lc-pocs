package com.dsantos.decorator;

public class BasicCar implements Car {

    @Override
    public String assemble() {
        return "Basic Car.";
    }

}
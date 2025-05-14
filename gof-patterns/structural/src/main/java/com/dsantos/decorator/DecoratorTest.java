package com.dsantos.decorator;

public class DecoratorTest {

    public static void main(String[] args) {
        Car sportsCar = new SportsCar(new BasicCar());
        System.out.println(sportsCar.assemble());
        System.out.println("*****");

        Car sportsLuxuryCar = new SportsCar(new LuxuryCar(new BasicCar()));
        System.out.println(sportsLuxuryCar.assemble());
    }
}

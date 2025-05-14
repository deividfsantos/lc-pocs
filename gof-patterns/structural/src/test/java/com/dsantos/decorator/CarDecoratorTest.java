package com.dsantos.decorator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarDecoratorTest {
    @Test
    public void testBasicCar() {
        Car basicCar = new BasicCar();
        assertEquals("Basic Car.", basicCar.assemble());
    }

    @Test
    public void testLuxuryCar() {
        Car luxuryCar = new LuxuryCar(new BasicCar());
        assertEquals("Basic Car. Adding features of Luxury Car.", luxuryCar.assemble());
    }

    @Test
    public void testLuxurySportsCar() {
        Car luxurySportsCar = new LuxuryCar(new SportsCar(new BasicCar()));
        assertEquals("Basic Car. Adding features of Sports Car. Adding features of Luxury Car.", luxurySportsCar.assemble());
    }

    @Test
    public void testMultipleDecorators() {
        Car fullyDecoratedCar = new SportsCar(new LuxuryCar(new SportsCar(new BasicCar())));
        assertEquals("Basic Car. Adding features of Sports Car. Adding features of Luxury Car. Adding features of Sports Car.", fullyDecoratedCar.assemble());
    }
}
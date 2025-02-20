package com.dsantos.abstractfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractFactoryTest {

    @Test
    void testConcreteFactory1() {
        AbstractFactory factory = new ConcreteFactory1();
        ProductA productA = factory.createProductA();
        ProductB productB = factory.createProductB();

        assertNotNull(productA);
        assertNotNull(productB);

        assertInstanceOf(ConcreteProductA1.class, productA);
        assertInstanceOf(ConcreteProductB1.class, productB);

        assertDoesNotThrow(productA::use);
        assertDoesNotThrow(productB::use);
    }

    @Test
    void testConcreteFactory2() {
        AbstractFactory factory = new ConcreteFactory2();
        ProductA productA = factory.createProductA();
        ProductB productB = factory.createProductB();

        assertNotNull(productA);
        assertNotNull(productB);

        assertInstanceOf(ConcreteProductA2.class, productA);
        assertInstanceOf(ConcreteProductB2.class, productB);

        assertDoesNotThrow(productA::use);
        assertDoesNotThrow(productB::use);
    }
}
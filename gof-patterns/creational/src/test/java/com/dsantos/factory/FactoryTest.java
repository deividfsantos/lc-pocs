package com.dsantos.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FactoryTest {
    @Test
    void testCreateProductA() {
        Product productA = Factory.createProduct("A");
        assertNotNull(productA);
        assertInstanceOf(ConcreteProductA.class, productA);
        productA.use();
    }

    @Test
    void testCreateProductB() {
        Product productB = Factory.createProduct("B");
        assertNotNull(productB);
        assertInstanceOf(ConcreteProductB.class, productB);
        productB.use();
    }

    @Test
    void testCreateProductWithUnknownType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Factory.createProduct("C");
        });
        assertEquals("Unknown product type: C", exception.getMessage());
    }
}
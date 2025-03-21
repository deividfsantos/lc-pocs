package com.dsantos.adapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SquarePegAdapterTest {

    @Test
    public void testGetRadius() {
        SquarePeg smallSquarePeg = new SquarePeg(5);
        SquarePegAdapter smallSquarePegAdapter = new SquarePegAdapter(smallSquarePeg);
        assertEquals(3.535, smallSquarePegAdapter.getRadius(), 0.001);

        SquarePeg largeSquarePeg = new SquarePeg(10);
        SquarePegAdapter largeSquarePegAdapter = new SquarePegAdapter(largeSquarePeg);
        assertEquals(7.071, largeSquarePegAdapter.getRadius(), 0.001);
    }
}
package com.dsantos.bridge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BridgePatternTest {

    @Test
    public void testTriangleWithRedColor() {
        Shape tri = new Triangle(new RedColor());
        assertEquals("Triangle filled with color red.", tri.applyColor());
    }

    @Test
    public void testPentagonWithGreenColor() {
        Shape pent = new Pentagon(new GreenColor());
        assertEquals("Pentagon filled with color green.", pent.applyColor());
    }
}
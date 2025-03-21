package com.dsantos.adapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoundHoleTest {

    @Test
    public void testRoundPegFits() {
        RoundHole hole = new RoundHole(5);
        RoundPeg rpeg = new RoundPeg(5);
        RoundPeg rpegBig = new RoundPeg(7);
        assertTrue(hole.fits(rpeg));
        assertFalse(hole.fits(rpegBig));

    }

    @Test
    public void testSquarePegFits() {
        RoundHole hole = new RoundHole(5);

        SquarePeg smallSquarePeg = new SquarePeg(2);
        SquarePegAdapter smallSquarePegAdapter = new SquarePegAdapter(smallSquarePeg);
        assertTrue(hole.fits(smallSquarePegAdapter));

        SquarePeg largeSquarePeg = new SquarePeg(20);
        SquarePegAdapter largeSquarePegAdapter = new SquarePegAdapter(largeSquarePeg);
        assertFalse(hole.fits(largeSquarePegAdapter));
    }
}
package com.dsantos.chainofresponsability;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ATMDispenseChainTest {
    ATMDispenseChain atmDispenser;

    @BeforeEach
    void setUp() {
        atmDispenser = new ATMDispenseChain();
    }

    @Test
    void testDispense680() {
        int dispense = atmDispenser.getC1().dispense(new Currency(680));
        assertEquals(15, dispense); // 13 notas de 50 + 1 de 20 + 1 de 10
    }

    @Test
    void testDispense450() {
        int dispense = atmDispenser.getC1().dispense(new Currency(450));
        assertEquals(9, dispense);
    }

    @Test
    void testDispense200() {
        int dispense = atmDispenser.getC1().dispense(new Currency(200));
        assertEquals(4, dispense);
    }

    @Test
    void testDispense1000() {
        int dispense = atmDispenser.getC1().dispense(new Currency(1000));
        assertEquals(20, dispense);
    }

    @Test
    void testDispense50() {
        int dispense = atmDispenser.getC1().dispense(new Currency(50));
        assertEquals(1, dispense);
    }

    @Test
    void testDispense20() {
        int dispense = atmDispenser.getC1().dispense(new Currency(20));
        assertEquals(1, dispense);
    }

    @Test
    void testDispense10() {
        int dispense = atmDispenser.getC1().dispense(new Currency(10));
        assertEquals(1, dispense);
    }

    @Test
    void testDispenseMultipleNotes() {
        int dispense = atmDispenser.getC1().dispense(new Currency(130));
        assertEquals(4, dispense);
    }

    @Test
    void testDispenseLargeAmount() {
        int dispense = atmDispenser.getC1().dispense(new Currency(5000));
        assertTrue(dispense > 0);
    }
}

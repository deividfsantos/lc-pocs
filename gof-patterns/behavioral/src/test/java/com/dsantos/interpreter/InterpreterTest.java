package com.dsantos.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterTest {

    @Test
    public void testSimpleAddition() {
        Expression expr = Interpreter.parse("10 + 5");
        assertEquals(15, expr.interpret());
    }

    @Test
    public void testAdditionAndSubtraction() {
        Expression expr = Interpreter.parse("10 + 5 - 3");
        assertEquals(12, expr.interpret());
    }

    @Test
    public void testNegativeResult() {
        Expression expr = Interpreter.parse("2 - 5");
        assertEquals(-3, expr.interpret());
    }
}

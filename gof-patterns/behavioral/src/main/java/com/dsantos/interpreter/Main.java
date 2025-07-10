package com.dsantos.interpreter;

public class Main {
    public static void main(String[] args) {
        String input = "5 + 3 - 2";
        Expression expression = Interpreter.parse(input);
        int result = expression.interpret();
        System.out.println("Result: " + result); // Output: 6
    }
}

package com.dsantos.interpreter;

import java.util.Stack;

public class Interpreter {

    public static Expression parse(String expression) {
        String[] tokens = expression.split(" ");
        Stack<Expression> stack = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (token.equals("+")) {
                Expression left = stack.pop();
                Expression right = new NumberExpression(Integer.parseInt(tokens[++i]));
                stack.push(new PlusExpression(left, right));
            } else if (token.equals("-")) {
                Expression left = stack.pop();
                Expression right = new NumberExpression(Integer.parseInt(tokens[++i]));
                stack.push(new MinusExpression(left, right));
            } else {
                stack.push(new NumberExpression(Integer.parseInt(token)));
            }
        }

        return stack.pop();
    }
}

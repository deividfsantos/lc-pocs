package com.dsantos.hibernate;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;

public class SlowQueryInterceptor extends EmptyInterceptor {
    private static final long SLOW_QUERY_THRESHOLD = 1000; // 1 second
    long startTime = System.currentTimeMillis();

    @Override
    public void beforeTransactionCompletion(Transaction tx) {
        System.out.println("TEST QUERY GO");
        this.startTime = System.currentTimeMillis();
        super.beforeTransactionCompletion(tx);
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        super.afterTransactionCompletion(tx);
        long executionTime = System.currentTimeMillis() - startTime;

        if (executionTime > SLOW_QUERY_THRESHOLD) {
            System.out.println("Slow Query Detected: " + tx + " | Execution Time: " + executionTime + "ms");
        }

    }
}
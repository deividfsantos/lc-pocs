package com.dsantos.hibernate;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;

public class SlowQueryInterceptor extends EmptyInterceptor {
    private static final long SLOW_QUERY_THRESHOLD = 10000; // 10 seconds
    long startTime = System.currentTimeMillis();

    @Override
    public void beforeTransactionCompletion(Transaction tx) {
        this.startTime = System.currentTimeMillis();
        super.beforeTransactionCompletion(tx);
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        System.out.println("Detecting slow query " + tx.getStatus());
        super.afterTransactionCompletion(tx);
        long executionTime = System.currentTimeMillis() - startTime;

        if (executionTime > SLOW_QUERY_THRESHOLD) {
            System.out.println("Slow Query Detected: " + tx.getStatus() + " | Execution Time: " + executionTime + "ms");
        }

    }
}
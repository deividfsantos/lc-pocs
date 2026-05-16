package com.dsantos.ledger.exception;

import com.dsantos.ledger.model.Money;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String accountId, Money balance, Money requested) {
        super("Insufficient funds in account " + accountId + ": balance=" + balance + ", requested=" + requested);
    }
}


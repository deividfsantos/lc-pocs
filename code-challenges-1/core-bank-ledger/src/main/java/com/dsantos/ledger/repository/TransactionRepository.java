package com.dsantos.ledger.repository;

import com.dsantos.ledger.model.Transaction;
import com.dsantos.ledger.model.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private final List<Transaction> store = new ArrayList<>();

    public void save(Transaction transaction) {
        store.add(transaction);
    }

    public List<Transaction> findByAccountId(String accountId) {
        return store.stream()
                .filter(t -> t.accountId().equals(accountId))
                .toList();
    }

    public List<Transaction> findByAccountIdAndType(String accountId, TransactionType type) {
        return store.stream()
                .filter(t -> t.accountId().equals(accountId) && t.type() == type)
                .toList();
    }

    public List<Transaction> findAll() {
        return List.copyOf(store);
    }
}


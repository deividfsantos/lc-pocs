package com.dsantos.ledger.repository;

import com.dsantos.ledger.model.Account;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountRepository {

    private final Map<String, Account> store = new HashMap<>();

    public void save(Account account) {
        store.put(account.id(), account);
    }

    public Optional<Account> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Collection<Account> findAll() {
        return store.values();
    }

    public boolean existsById(String id) {
        return store.containsKey(id);
    }

    public void delete(String id) {
        store.remove(id);
    }
}


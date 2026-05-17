package com.dsantos.ledger.service;

import com.dsantos.ledger.exception.AccountNotFoundException;
import com.dsantos.ledger.model.Account;
import com.dsantos.ledger.repository.AccountRepository;

import java.util.Collection;

public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account open(String id, String ownerName, String currency) {
        if (repository.existsById(id)) {
            throw new IllegalArgumentException("Account already exists: " + id);
        }
        Account account = Account.open(id, ownerName, currency);
        repository.save(account);
        return account;
    }

    public Account findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Collection<Account> findAll() {
        return repository.findAll();
    }

    public void close(String id) {
        Account account = findById(id);
        Account closed = new Account(account.id(), account.ownerName(), account.currency(),
                Account.AccountStatus.CLOSED, account.createdAt());
        repository.save(closed);
    }
}


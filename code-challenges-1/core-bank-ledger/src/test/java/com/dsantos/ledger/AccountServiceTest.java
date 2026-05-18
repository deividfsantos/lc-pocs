package com.dsantos.ledger;

import com.dsantos.ledger.exception.AccountNotFoundException;
import com.dsantos.ledger.model.Account;
import com.dsantos.ledger.repository.AccountRepository;
import com.dsantos.ledger.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService service;

    @BeforeEach
    void setUp() {
        service = new AccountService(new AccountRepository());
    }

    @Test
    void opensNewAccount() {
        Account account = service.open("ACC-1", "Alice", "USD");
        assertEquals("ACC-1", account.id());
        assertEquals("Alice", account.ownerName());
        assertEquals(Account.AccountStatus.ACTIVE, account.status());
    }

    @Test
    void throwsWhenDuplicateId() {
        service.open("ACC-1", "Alice", "USD");
        assertThrows(IllegalArgumentException.class, () -> service.open("ACC-1", "Bob", "USD"));
    }

    @Test
    void throwsWhenAccountNotFound() {
        assertThrows(AccountNotFoundException.class, () -> service.findById("NONE"));
    }

    @Test
    void closesAccount() {
        service.open("ACC-1", "Alice", "USD");
        service.close("ACC-1");
        Account account = service.findById("ACC-1");
        assertEquals(Account.AccountStatus.CLOSED, account.status());
    }

    @Test
    void findsAllAccounts() {
        service.open("A1", "Alice", "USD");
        service.open("A2", "Bob", "USD");
        assertEquals(2, service.findAll().size());
    }
}


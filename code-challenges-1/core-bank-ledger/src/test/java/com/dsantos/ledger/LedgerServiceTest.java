package com.dsantos.ledger;

import com.dsantos.ledger.exception.InsufficientFundsException;
import com.dsantos.ledger.model.Money;
import com.dsantos.ledger.model.Transaction;
import com.dsantos.ledger.model.TransactionType;
import com.dsantos.ledger.repository.AccountRepository;
import com.dsantos.ledger.repository.TransactionRepository;
import com.dsantos.ledger.service.AccountService;
import com.dsantos.ledger.service.LedgerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LedgerServiceTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private AccountService accountService;
    private LedgerService ledger;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
        accountService = new AccountService(accountRepository);
        ledger = new LedgerService(accountRepository, transactionRepository);
    }

    @Test
    void creditIncreasesBalance() {
        accountService.open("A1", "Alice", "USD");
        ledger.credit("A1", Money.of(new BigDecimal("100.00"), "USD"), "deposit");

        Money balance = ledger.getBalance("A1");
        assertEquals(new BigDecimal("100.00"), balance.amount());
    }

    @Test
    void debitDecreasesBalance() {
        accountService.open("A1", "Alice", "USD");
        ledger.credit("A1", Money.of(new BigDecimal("200.00"), "USD"), "deposit");
        ledger.debit("A1", Money.of(new BigDecimal("50.00"), "USD"), "purchase");

        Money balance = ledger.getBalance("A1");
        assertEquals(new BigDecimal("150.00"), balance.amount());
    }

    @Test
    void debitThrowsWhenInsufficientFunds() {
        accountService.open("A1", "Alice", "USD");
        ledger.credit("A1", Money.of(new BigDecimal("10.00"), "USD"), "deposit");

        assertThrows(InsufficientFundsException.class, () ->
                ledger.debit("A1", Money.of(new BigDecimal("50.00"), "USD"), "purchase"));
    }

    @Test
    void transferMovesMoneyBetweenAccounts() {
        accountService.open("A1", "Alice", "USD");
        accountService.open("A2", "Bob", "USD");
        ledger.credit("A1", Money.of(new BigDecimal("500.00"), "USD"), "deposit");

        ledger.transfer("A1", "A2", Money.of(new BigDecimal("200.00"), "USD"), "payment");

        assertEquals(new BigDecimal("300.00"), ledger.getBalance("A1").amount());
        assertEquals(new BigDecimal("200.00"), ledger.getBalance("A2").amount());
    }

    @Test
    void historyContainsAllTransactions() {
        accountService.open("A1", "Alice", "USD");
        ledger.credit("A1", Money.of(new BigDecimal("100.00"), "USD"), "deposit");
        ledger.debit("A1", Money.of(new BigDecimal("30.00"), "USD"), "purchase");

        List<Transaction> history = ledger.getHistory("A1");
        assertEquals(2, history.size());
        assertEquals(TransactionType.CREDIT, history.get(0).type());
        assertEquals(TransactionType.DEBIT, history.get(1).type());
    }

    @Test
    void balanceStartsAtZero() {
        accountService.open("A1", "Alice", "USD");
        Money balance = ledger.getBalance("A1");
        assertEquals(BigDecimal.ZERO, balance.amount());
    }
}


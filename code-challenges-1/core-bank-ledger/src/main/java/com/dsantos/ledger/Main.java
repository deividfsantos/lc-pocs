package com.dsantos.ledger;

import com.dsantos.ledger.model.Account;
import com.dsantos.ledger.model.Money;
import com.dsantos.ledger.model.Transaction;
import com.dsantos.ledger.repository.AccountRepository;
import com.dsantos.ledger.repository.TransactionRepository;
import com.dsantos.ledger.service.AccountService;
import com.dsantos.ledger.service.LedgerService;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        AccountRepository accountRepo = new AccountRepository();
        TransactionRepository transactionRepo = new TransactionRepository();

        AccountService accountService = new AccountService(accountRepo);
        LedgerService ledger = new LedgerService(accountRepo, transactionRepo);

        Account alice = accountService.open("ACC-001", "Alice", "USD");
        Account bob = accountService.open("ACC-002", "Bob", "USD");

        System.out.println("Opened accounts: " + alice.id() + ", " + bob.id());

        ledger.credit("ACC-001", Money.of(new BigDecimal("1000.00"), "USD"), "Initial deposit");
        ledger.credit("ACC-002", Money.of(new BigDecimal("500.00"), "USD"), "Initial deposit");

        System.out.println("Alice balance: " + ledger.getBalance("ACC-001"));
        System.out.println("Bob balance: " + ledger.getBalance("ACC-002"));

        ledger.transfer("ACC-001", "ACC-002", Money.of(new BigDecimal("250.00"), "USD"), "Payment");

        System.out.println("After transfer:");
        System.out.println("Alice balance: " + ledger.getBalance("ACC-001"));
        System.out.println("Bob balance: " + ledger.getBalance("ACC-002"));

        System.out.println("\nAlice transaction history:");
        for (Transaction tx : ledger.getHistory("ACC-001")) {
            System.out.println("  " + tx.type() + " " + tx.amount() + " - " + tx.description());
        }
    }
}


package com.dsantos.ledger.service;

import com.dsantos.ledger.exception.AccountNotFoundException;
import com.dsantos.ledger.exception.InsufficientFundsException;
import com.dsantos.ledger.model.Account;
import com.dsantos.ledger.model.Money;
import com.dsantos.ledger.model.Transaction;
import com.dsantos.ledger.model.TransactionType;
import com.dsantos.ledger.repository.AccountRepository;
import com.dsantos.ledger.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

public class LedgerService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public LedgerService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction credit(String accountId, Money amount, String description) {
        Account account = getActiveAccount(accountId);
        Transaction tx = Transaction.credit(accountId, amount, description);
        transactionRepository.save(tx);
        return tx;
    }

    public Transaction debit(String accountId, Money amount, String description) {
        Account account = getActiveAccount(accountId);
        Money balance = getBalance(accountId);
        if (balance.subtract(amount).isNegative()) {
            throw new InsufficientFundsException(accountId, balance, amount);
        }
        Transaction tx = Transaction.debit(accountId, amount, description);
        transactionRepository.save(tx);
        return tx;
    }

    public Money getBalance(String accountId) {
        Account account = getActiveAccount(accountId);
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        Money balance = Money.zero(account.currency());
        for (Transaction tx : transactions) {
            balance = switch (tx.type()) {
                case CREDIT -> balance.add(tx.amount());
                case DEBIT -> balance.subtract(tx.amount());
            };
        }
        return balance;
    }

    public List<Transaction> getHistory(String accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException(accountId);
        }
        return transactionRepository.findByAccountId(accountId);
    }

    public void transfer(String fromAccountId, String toAccountId, Money amount, String description) {
        debit(fromAccountId, amount, "Transfer to " + toAccountId + ": " + description);
        credit(toAccountId, amount, "Transfer from " + fromAccountId + ": " + description);
    }

    private Account getActiveAccount(String accountId) {
        return accountRepository.findById(accountId)
                .filter(Account::isActive)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}


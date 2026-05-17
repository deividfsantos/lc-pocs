package com.dsantos.ledger.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(
        UUID id,
        String accountId,
        TransactionType type,
        Money amount,
        String description,
        LocalDateTime createdAt
) {
    public static Transaction credit(String accountId, Money amount, String description) {
        return new Transaction(UUID.randomUUID(), accountId, TransactionType.CREDIT, amount, description, LocalDateTime.now());
    }

    public static Transaction debit(String accountId, Money amount, String description) {
        return new Transaction(UUID.randomUUID(), accountId, TransactionType.DEBIT, amount, description, LocalDateTime.now());
    }
}


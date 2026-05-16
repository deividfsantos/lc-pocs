package com.dsantos.ledger.model;

import java.time.LocalDateTime;

public record Account(
        String id,
        String ownerName,
        String currency,
        AccountStatus status,
        LocalDateTime createdAt
) {
    public static Account open(String id, String ownerName, String currency) {
        return new Account(id, ownerName, currency, AccountStatus.ACTIVE, LocalDateTime.now());
    }

    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }

    public enum AccountStatus {
        ACTIVE,
        SUSPENDED,
        CLOSED
    }
}


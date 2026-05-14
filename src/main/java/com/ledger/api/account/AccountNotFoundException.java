package com.ledger.api.account;

import com.ledger.api.shared.DomainException;

import java.util.UUID;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException(UUID id) {
        super("Account " + id + " not found");
    }
}

package com.ledger.api.transfer;

import com.ledger.api.shared.DomainException;

public class CurrencyMismatchException extends DomainException {

    public CurrencyMismatchException(String sourceCurrency, String destinationCurrency) {
        super("Currency mismatch: source account currency " + sourceCurrency + " does not match destination account currency "
                + destinationCurrency);
    }
}

package com.ledger.api.transfer;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferResponse(UUID id, UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount,
                               String currency, String status, String createdAt) {

    public static TransferResponse of(Transfer transfer) {
        return new TransferResponse(
                transfer.getId(),
                transfer.getSourceAccountId(),
                transfer.getDestinationAccountId(),
                transfer.getAmount(),
                transfer.getCurrency(),
                transfer.getStatus(),
                transfer.getCreatedAt().toString()
        );
    }
}

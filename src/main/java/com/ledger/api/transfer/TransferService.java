package com.ledger.api.transfer;

import com.ledger.api.account.Account;
import com.ledger.api.account.AccountNotFoundException;
import com.ledger.api.account.AccountRepository;
import com.ledger.api.ledger.LedgerEntry;
import com.ledger.api.ledger.LedgerEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class TransferService {

    private final TransferRepository transferRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final AccountRepository accountRepository;

    public TransferService(TransferRepository transferRepository, LedgerEntryRepository ledgerEntryRepository,
                           AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.accountRepository = accountRepository;
    }

    public Transfer createTransfer(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {

        Account sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFoundException(sourceAccountId));

        Account destinationAccount = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new AccountNotFoundException(destinationAccountId));

        if(!sourceAccount.getCurrency().equals(destinationAccount.getCurrency())) {
            throw new CurrencyMismatchException(sourceAccount.getCurrency(), destinationAccount.getCurrency());
        }

        var transfer = new Transfer();
        transfer.setSourceAccountId(sourceAccountId);
        transfer.setDestinationAccountId(destinationAccountId);
        transfer.setAmount(amount);
        transfer.setCurrency(sourceAccount.getCurrency());
        transfer.setStatus("COMPLETED");
        var savedTransfer = transferRepository.save(transfer);
        ledgerEntryRepository.save(buildEntry(savedTransfer.getId(), sourceAccountId, amount, "DEBIT"));
        ledgerEntryRepository.save(buildEntry(savedTransfer.getId(), destinationAccountId, amount, "CREDIT"));
        return savedTransfer;
    }

    private LedgerEntry buildEntry(UUID transferId, UUID accountId, BigDecimal amount, String entryType) {
        var entry = new LedgerEntry();
        entry.setTransferId(transferId);
        entry.setAccountId(accountId);
        entry.setAmount(amount);
        entry.setEntryType(entryType);
        return entry;
    }

}

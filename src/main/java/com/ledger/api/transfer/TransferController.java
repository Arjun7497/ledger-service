package com.ledger.api.transfer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferResponse createTransfer(@RequestBody CreateTransferRequest request) {
        var transfer = transferService.createTransfer(
                request.sourceAccountId(),
                request.destinationAccountId(),
                request.amount()
        );
        return TransferResponse.of(transfer);
    }
}

CREATE TABLE transfer (
    id              UUID        NOT NULL DEFAULT gen_random_uuid(),
    source_account_id   UUID    NOT NULL,
    destination_account_id  UUID NOT NULL ,
    amount      NUMERIC(19,4)  NOT NULL ,
    currency    VARCHAR(3)     NOT NULL ,
    status      VARCHAR(20)    NOT NULL DEFAULT 'COMPLETED',
    created_at  TIMESTAMP      NOT NULL DEFAULT now(),

    CONSTRAINT pk_transfer  PRIMARY KEY (id),
    CONSTRAINT fk_transfer_source FOREIGN KEY (source_account_id) REFERENCES account(id),
    CONSTRAINT fk_transfer_destination FOREIGN KEY (destination_account_id) REFERENCES account(id),
    CONSTRAINT chk_transfer_amount CHECK ( amount > 0 ),
    CONSTRAINT chk_transfer_different_accounts CHECK ( source_account_id != destination_account_id )
);

CREATE TABLE ledger_entry (
    id      UUID      NOT NULL DEFAULT gen_random_uuid(),
    transfer_id  UUID  NOT NULL ,
    account_id   UUID  NOT NULL ,
    amount       NUMERIC(19,4) NOT NULL ,
    entry_type   VARCHAR(10)  NOT NULL ,
    created_at   TIMESTAMP  NOT NULL DEFAULT now(),

    CONSTRAINT pk_ledger_entry PRIMARY KEY (id),
    CONSTRAINT fk_ledger_entry_transfer FOREIGN KEY (transfer_id) REFERENCES transfer(id),
    CONSTRAINT fk_ledger_entry_account FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT chk_entry_type CHECK ( entry_type IN ('DEBIT', 'CREDIT'))
);
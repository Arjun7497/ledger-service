CREATE TABLE account (
    id          UUID            NOT NULL DEFAULT gen_random_uuid(),
    name        VARCHAR(255)    NOT NULL,
    currency    VARCHAR(3)      NOT NULL,
    version     BIGINT          NOT NULL DEFAULT 0,
    created_at  TIMESTAMP       NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP       NOT NULL DEFAULT now(),

    CONSTRAINT pk_account PRIMARY KEY (id)
);
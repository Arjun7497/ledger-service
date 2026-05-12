package main.java.com.ledger.api.account;

import java.util.UUID;

public record AccountResponse(UUID id,String name,String currency,String createdAt){

public static AccountResponse of(Account account){return new AccountResponse(account.getId(),account.getName(),account.getCurrency(),account.getCreatedAt().toString());}}
package org.kiroff.bank.account.query.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.kiroff.bank.account.query.api.dto.AccountLookupRequest;
import org.kiroff.bank.account.query.api.dto.AccountLookupResponse;
import org.kiroff.bank.account.query.api.dto.EqualityType;
import org.kiroff.bank.account.query.api.queries.FindAccountByHolderQuery;
import org.kiroff.bank.account.query.api.queries.FindAccountsByBalanceQuery;
import org.kiroff.bank.account.query.api.queries.FindAccountsByIdQuery;
import org.kiroff.bank.account.query.api.queries.FindAllAccountsQuery;
import org.kiroff.bank.account.query.domain.BankAccount;
import org.kiroff.bank.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/accounts")
public class AccountLookupController {

    private final QueryDispatcher queryDispatcher;

    public AccountLookupController(QueryDispatcher queryDispatcher) {
        this.queryDispatcher = queryDispatcher;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountsByIdQuery(id));
            if(accounts == null || accounts.isEmpty())
            {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Found the account")
                    .build());
        } catch (Exception e) {
            log.error("Failed to get accounts by id", e);
            return ResponseEntity.internalServerError().body(new AccountLookupResponse(e.toString()));
        }
    }

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAccountByParams(@RequestBody AccountLookupRequest request) {
        if(request != null && !request.isEmpty())
        {
            if(request.getAccountHolder() != null) {
                return findAccountsByHolder(request);
            } else if (request.getBalance() != null) {
                return findAccountsByBalance(request);
            }
        }
        return getAllAccounts();
    }

    private ResponseEntity<AccountLookupResponse> findAccountsByHolder(AccountLookupRequest request) {
        try {
            final String accountHolder = request.getAccountHolder();
            if(accountHolder == null || accountHolder.isEmpty()) {
                return ResponseEntity.badRequest().body(new AccountLookupResponse("Account holder is required"));
            }
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if(accounts == null || accounts.isEmpty())
            {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Found {0} account(s)", accounts.size()))
                    .build());
        } catch (Exception e) {
            log.error("Failed to get accounts by holder", e);
            return ResponseEntity.internalServerError().body(new AccountLookupResponse(e.toString()));
        }
    }

    private ResponseEntity<AccountLookupResponse> findAccountsByBalance(AccountLookupRequest request) {
        var value = 0.0;
        try {
            final String balance = request.getBalance();
            if(balance == null || balance.isEmpty()) {
                return ResponseEntity.badRequest().body(new AccountLookupResponse("Account balance is required"));
            } else {
                try {
                    value = Double.parseDouble(balance);
                } catch (NumberFormatException nfe) {
                    return ResponseEntity.badRequest().body(new AccountLookupResponse("Account balance must be a number"));
                }
            }
            final EqualityType type = request.getEqualityType();
            if(type == null) {
                return ResponseEntity.badRequest().body(new AccountLookupResponse("Account balance equality type is required"));
            }
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountsByBalanceQuery(type, value));
            if(accounts == null || accounts.isEmpty())
            {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Found {0} account(s)", accounts.size()))
                    .build());
        } catch (Exception e) {
            log.error("Failed to get accounts by balance and equality type", e);
            return ResponseEntity.internalServerError().body(new AccountLookupResponse(e.toString()));
        }
    }

    private ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
            if(accounts == null || accounts.isEmpty())
            {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Found {0} account(s)", accounts.size()))
                    .build());
        } catch (Exception e) {
            log.error("Failed to get all accounts", e);
            return ResponseEntity.internalServerError().body(new AccountLookupResponse(e.toString()));
        }
    }
}

package org.kiroff.bank.account.query.handlers;

import lombok.Builder;
import org.kiroff.bank.account.query.domain.AccountRepository;
import org.kiroff.bank.account.query.domain.BankAccount;
import org.kiroff.bank.account.common.events.AccountClosedEvent;
import org.kiroff.bank.account.common.events.AccountOpenedEvent;
import org.kiroff.bank.account.common.events.FundsDepositedEvent;
import org.kiroff.bank.account.common.events.FundsWithdrawnEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountEventHandler implements EventHandler {

    private final AccountRepository accountRepository;

    @Builder
    public AccountEventHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void on(AccountOpenedEvent event) {
        var account = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .creationDate(LocalDateTime.now())
                .build();
        accountRepository.save(account);
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }

    @Override
    public void on(FundsDepositedEvent event) {
        accountRepository.findById(event.getId()).ifPresent(account -> {
            account.setBalance(account.getBalance() + event.getAmount());
            accountRepository.save(account);
        });

    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        accountRepository.findById(event.getId()).ifPresent(account -> {
            account.setBalance(account.getBalance() - event.getAmount());
            accountRepository.save(account);
        });
    }
}

package org.kiroff.bank.account.query.api.queries;

import org.kiroff.bank.account.query.api.dto.EqualityType;
import org.kiroff.bank.account.query.domain.AccountRepository;
import org.kiroff.bank.cqrs.core.domain.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AccountQueryHandler implements QueryHandler {

    private final AccountRepository accountRepository;

    public AccountQueryHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        final List<BaseEntity> accounts = new LinkedList<>();
        StreamSupport.stream(accountRepository.findAll().spliterator(), false).forEach(accounts::add);
        return accounts;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsByBalanceQuery query) {
        return switch (query.getEqualityType()) {
            case EqualityType.GREATER_THAN ->
                    new LinkedList<>(accountRepository.findByBalanceGreaterThan(query.getBalance()));
            case EqualityType.LESS_THAN ->
                    new LinkedList<>(accountRepository.findByBalanceLessThan(query.getBalance()));
            case EqualityType.EQUAL ->
                    new LinkedList<>(accountRepository.findByBalanceEquals(query.getBalance()));
        };
    }

    @Override
    public List<BaseEntity> handle(FindAccountsByIdQuery query) {
        final List<BaseEntity> accounts = new LinkedList<>();
        accountRepository.findById(query.getId()).ifPresent(accounts::add);
        return accounts;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        final List<BaseEntity> accounts = new LinkedList<>();
        accountRepository.findByAccountHolder(query.getAccountHolder()).ifPresent(accounts::add);
        return accounts;
    }
}

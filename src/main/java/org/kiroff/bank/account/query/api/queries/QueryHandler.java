package org.kiroff.bank.account.query.api.queries;

import org.kiroff.bank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccountsQuery query);
    List<BaseEntity> handle(FindAccountsByBalanceQuery query);
    List<BaseEntity> handle(FindAccountsByIdQuery query);
    List<BaseEntity> handle(FindAccountByHolderQuery query);
}

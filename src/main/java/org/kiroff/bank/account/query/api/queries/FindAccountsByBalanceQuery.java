package org.kiroff.bank.account.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kiroff.bank.account.query.api.dto.EqualityType;
import org.kiroff.bank.cqrs.core.queries.BaseQuery;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FindAccountsByBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}

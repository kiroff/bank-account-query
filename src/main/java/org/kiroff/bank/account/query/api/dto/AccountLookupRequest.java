package org.kiroff.bank.account.query.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kiroff.bank.account.common.dto.BaseRequest;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AccountLookupRequest extends BaseRequest {
    String accountHolder;
    EqualityType equalityType;
    String balance;

    public boolean isEmpty() {
        return (accountHolder == null || accountHolder.isEmpty())
                && (balance == null || balance.isEmpty())
                && equalityType == null;
    }
}

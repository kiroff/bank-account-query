package org.kiroff.bank.account.query.handlers;

import org.kiroff.bank.account.common.events.AccountClosedEvent;
import org.kiroff.bank.account.common.events.AccountOpenedEvent;
import org.kiroff.bank.account.common.events.FundsDepositedEvent;
import org.kiroff.bank.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(AccountClosedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
}

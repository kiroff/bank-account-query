package org.kiroff.bank.account.query.consumers;

//import org.kiroff.bank.account.common.events.AccountClosedEvent;
//import org.kiroff.bank.account.common.events.AccountOpenedEvent;
//import org.kiroff.bank.account.common.events.FundsDepositedEvent;
//import org.kiroff.bank.account.common.events.FundsWithdrawnEvent;
import org.kiroff.bank.cqrs.core.events.BaseEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
//    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
//    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
//    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
//    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload BaseEvent event, Acknowledgment ack);
}

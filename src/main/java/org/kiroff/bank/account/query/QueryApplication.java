package org.kiroff.bank.account.query;

import jakarta.annotation.PostConstruct;
import org.kiroff.bank.account.query.api.queries.FindAccountByHolderQuery;
import org.kiroff.bank.account.query.api.queries.FindAccountsByBalanceQuery;
import org.kiroff.bank.account.query.api.queries.FindAccountsByIdQuery;
import org.kiroff.bank.account.query.api.queries.FindAllAccountsQuery;
import org.kiroff.bank.account.query.api.queries.QueryHandler;
import org.kiroff.bank.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueryApplication {

    private final QueryHandler queryHandler;
    
    private final QueryDispatcher queryDispatcher;

    public QueryApplication(QueryHandler queryHandler, QueryDispatcher queryDispatcher) {
        this.queryHandler = queryHandler;
        this.queryDispatcher = queryDispatcher;
    }

    static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAccountsByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAccountsByBalanceQuery.class, queryHandler::handle);
    }
}

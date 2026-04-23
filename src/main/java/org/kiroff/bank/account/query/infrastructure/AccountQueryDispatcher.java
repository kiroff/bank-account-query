package org.kiroff.bank.account.query.infrastructure;

import org.kiroff.bank.cqrs.core.domain.BaseEntity;
import org.kiroff.bank.cqrs.core.infrastructure.QueryDispatcher;
import org.kiroff.bank.cqrs.core.queries.BaseQuery;
import org.kiroff.bank.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, _ -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = Optional.ofNullable(routes.get(query.getClass()))
                .orElseThrow(() -> new RuntimeException("No query handler method found"));
        if (handlers.isEmpty()) {
            throw new RuntimeException("No query handler method found");
        } else if (handlers.size() == 1) {
            return handlers.getFirst().handle(query);
        } else {
            throw new IllegalStateException("More than one command handler for type " + query.getClass());
        }
    }
}

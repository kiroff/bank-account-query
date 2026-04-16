# Bank Account Query Microservice

This microservice handles the **Query** side of the [Bank Account Management System](../README.md). It consumes events from Kafka to maintain a read-optimized view of bank account data in a PostgreSQL database.

## Key Features

- **Read-optimized Model**: Uses JPA and PostgreSQL to store account views that are optimized for reading.
- **Event Consumption**: Listens for `AccountOpenedEvent`, `FundsDepositedEvent`, `FundsWithdrawnEvent`, and `AccountClosedEvent` on Kafka topics.
- **CQRS Architecture**: Separates data modification (commands) from data retrieval (queries).

## Configuration

Settings can be found in `src/main/resources/application.yaml`, including:
- Server port (`5001`)
- PostgreSQL database connection
- Kafka bootstrap servers and consumer group configuration

Refer to the main [README](../README.md) for setup and build instructions.
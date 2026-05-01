# OrderFlow: Distributed Order Processing System

A production-style backend system that simulates order processing with a focus on scalability, performance, and real-world backend architecture.
This project is built to practice system design concepts and backend engineering using modern technologies.


## Architecture

- REST APIs using Spring Boot
- PostgreSQL for persistence
- Redis for caching
- Kafka for asynchronous processing
- Retry mechanism for fault tolerance
- Dead Letter Queue for failure handling


## Tech Stack

- Java 21
- Spring Boot
- PostgreSQL
- Redis (Caching)
- Kafka (planned)
- Docker


## Features

- Create Order API
- Fetch Order API
- PostgreSQL integration using JPA
- Redis caching for faster reads


## Upcoming Features

- Cache eviction strategy
- Kafka-based asynchronous order processing
- Order status workflow (CREATED → COMPLETED)
- Frontend integration
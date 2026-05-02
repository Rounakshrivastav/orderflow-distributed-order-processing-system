# OrderFlow: Distributed Order Processing System

A production-style backend system that simulates order processing with a focus on scalability, performance, and real-world backend architecture.
This project is built to practice system design concepts and backend engineering using modern technologies.
A full-stack distributed order processing system built using Spring Boot, Kafka, Redis, PostgreSQL, and React. The system demonstrates asynchronous processing, caching, and fault tolerance with retry and dead-letter queue mechanisms.


## Architecture

Client → REST API → DB (PostgreSQL)
                  ↓
               Redis Cache
                  ↓
               Kafka (Event)
                  ↓
           Consumer Processing
                  ↓
         Update DB + Evict Cache


## Tech Stack

- Backend: Java, Spring Boot
- Database: PostgreSQL
- Messaging: Apache Kafka
- Caching: Redis
- Frontend: React (Vite)
- Build Tool: Maven
- Containerization: Docker


## Features

- Create and track orders via REST APIs
- Asynchronous order processing using Kafka
- Retry mechanism for failure handling
- Dead Letter Queue (DLQ) for failed events
- Redis caching with cache eviction
- Real-time order status tracking via frontend


## Future Enhancements

- Order state validation (strict transitions)
- Authentication & authorization
- Order history dashboard
- Docker Compose setup
- Microservices decomposition  

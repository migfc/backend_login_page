# Quick Start

This file contains a short guide to run the application, tests and Docker compose with PostgreSQL.

## Run locally

```bash
mvn clean package
mvn spring-boot:run
```

## Using Docker Compose (Postgres)

```bash
cp .env.example .env
# edit .env and set secure values

docker compose up -d
```

## Tests

```bash
mvn test
mvn clean test jacoco:report
```

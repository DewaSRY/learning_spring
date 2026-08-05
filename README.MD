---
title: Learn Basic Spring Boot
---

# Basic Spring Boot Application

A learning project that demonstrates how to build a RESTful web application using Spring Boot 4, covering core concepts such as layered architecture, database migrations, Spring Security, validation, and integration testing.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Language |
| Spring Boot | 4.1.0 | Application framework |
| Spring Web MVC | — | REST API layer |
| Spring Data JPA | — | ORM / data access |
| Spring Security | — | Authentication & authorization |
| Spring Validation | — | Request body validation |
| Flyway | — | Database schema migration |
| MySQL | 8 | Relational database |
| Lombok | — | Boilerplate reduction |
| Testcontainers | 1.21.3 | Integration testing with real DB |
| Docker Compose | — | Local development environment |

---

## Architecture

The project follows a classic **layered architecture**:

```
HTTP Request
     │
     ▼
┌─────────────┐
│  Controller │  ← REST endpoints, request/response mapping
└──────┬──────┘
       │
┌──────▼──────┐
│   Service   │  ← Business logic
└──────┬──────┘
       │
┌──────▼──────┐
│ Repository  │  ← Spring Data JPA, database queries
└──────┬──────┘
       │
┌──────▼──────┐
│    MySQL    │  ← Persistent storage
└─────────────┘
```

### Package Structure

```
src/main/java/com/sdewa/BasicSpring/
├── bootstrap/       # Startup data initialization (CSV loader)
├── config/          # OpenAPI / Swagger configuration
├── controllers/     # REST controllers
├── exception/       # Custom exceptions & global exception handler
├── models/          # JPA entities, request DTOs, query/response models
├── repositories/    # Spring Data JPA repositories
└── services/        # Business logic interfaces and implementations
```

---

## Domain Model

```
account ─────────────── order ──────── order_items
   │                      │                 │
   │  (one account         │  (one order     │  (many items
   │   has many orders)    │   has many      │   each with qty)
   │                       │   items)        │
                                          beverage
```

### Database Tables (managed by Flyway)

- **`account`** — user accounts with name and unique number
- **`beverage`** — beverage catalog with name, description, and unique number
- **`order`** — a purchase order linked to an account
- **`order_items`** — line items joining an order to beverages with quantity
- **`order_views`** / **`order_item_views`** — SQL views for denormalized reads

---

## REST API

All endpoints are prefixed with `/api/v1`.

### Accounts — `/api/v1/accounts`

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/accounts` | List all accounts |
| GET | `/api/v1/accounts/{id}` | Get account by ID |
| POST | `/api/v1/accounts` | Create a new account |
| PUT | `/api/v1/accounts/{id}` | Update an account |
| DELETE | `/api/v1/accounts/{id}` | Delete an account |

### Beverages — `/api/v1/beverages`

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/beverages` | List all beverages |
| GET | `/api/v1/beverages/{id}` | Get beverage by ID |
| POST | `/api/v1/beverages` | Create a new beverage |
| PUT | `/api/v1/beverages/{id}` | Update a beverage |
| DELETE | `/api/v1/beverages/{id}` | Delete a beverage |

### Orders — `/api/v1/orders`

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/orders` | List orders (paginated) |
| POST | `/api/v1/orders` | Place a new order |

---

## Key Features

### 1. Database Migration with Flyway
Schema changes are version-controlled in `src/main/resources/db/migration/`:
- `V1__init_database.sql` — creates `account` and `beverage` tables
- `V2_order_beverage_feature.sql` — creates `order`, `order_items` tables and SQL views

### 2. Bootstrap Data Loader
On startup, `BootstrapInitData` reads `beverage_name_list.csv` and seeds the beverage table if it is empty.

### 3. Request Validation
DTOs use Jakarta Bean Validation annotations (`@Valid`). Invalid requests return structured error responses via `GlobalExceptionController`.

### 4. Global Exception Handling
`GlobalExceptionController` (`@RestControllerAdvice`) catches `CommonContentNotFound` and returns a consistent `CommonError` JSON response with the appropriate HTTP status.

### 5. Pagination
`GET /api/v1/orders` accepts query parameters through `OrderQuery` and returns a `PaginationDataResponse<OrderEntity>`.

### 6. Spring Security
Spring Security is configured on the classpath. Security rules can be customized in the `config` package.

### 7. Docker Compose Integration
`compose.yaml` defines a MySQL 8 service. Running the app in development mode automatically starts the container via `spring-boot-docker-compose`.

---

## Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose

### Run locally

```bash
# Start the application (Docker Compose for MySQL is managed automatically)
./gradlew bootRun
```

The application starts on `http://localhost:8080`.

### Run tests

```bash
./gradlew test
```

Tests use **Testcontainers** to spin up a real MySQL container, so no manual database setup is needed for testing.

---

## Project Configuration

Key settings in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/basicsSpringdb
    username: basicsSpring
    password: password
  jpa:
    hibernate:
      ddl-auto: validate   # Flyway owns schema; Hibernate only validates
  flyway:
    enabled: true
    locations: classpath:db/migration
``` 
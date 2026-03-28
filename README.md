# Market Price API

A REST API for tracking commodity prices across Zambian markets. Field agents submit price data, which is verified and made available for querying — latest prices, historical trends, and cross-market comparisons.

## Tech Stack

- **Java 21** + **Spring Boot 4.0.5**
- **PostgreSQL** — primary data store
- **Redis** — caching layer with smart TTL and eviction
- **Spring Data JPA** / Hibernate
- **Springdoc OpenAPI** — auto-generated Swagger docs

## Getting Started

### Prerequisites

- Java 21+
- PostgreSQL running on `localhost:5432`
- Redis running on `localhost:6379`
- Maven (or use the included `./mvnw` wrapper)

### Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE market_price_db;
```

The schema is auto-managed by Hibernate (`ddl-auto: update`).

### Configuration

All configuration lives in `src/main/resources/application.yml`. Update credentials as needed:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/market_price_db
    username: postgres
    password: your_password

  data:
    redis:
      host: localhost
      port: 6379
```

### Run

```bash
./mvnw spring-boot:run
```

The API starts on **port 8081**.

---

## API Reference

Base URL: `http://localhost:8081/api/v1`

Interactive docs available at: [`http://localhost:8081/swagger-ui.html`](http://localhost:8081/swagger-ui.html)

### Prices

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/prices` | Submit a new price entry |
| `GET` | `/prices/latest` | Get latest verified price for a commodity in a market |
| `GET` | `/prices/history` | Get price history (last N days) |
| `GET` | `/prices/compare` | Compare prices across markets in a city |

**Query parameters:**
- `latest` & `history`: `commodityId`, `marketId`
- `history`: optional `days` (default: `30`)
- `compare`: `commodityId`, `city`

**Submit a price entry:**
```json
POST /api/v1/prices
{
  "commodityId": 1,
  "marketId": 2,
  "agentId": 3,
  "price": 45.50,
  "unit": "kg",
  "notes": "Fresh stock"
}
```

### Commodities

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/commodities` | Create a commodity |
| `GET` | `/commodities` | List all active commodities |

### Markets

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/markets` | Create a market |
| `GET` | `/markets` | List all active markets |

### Agents

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/agents` | Register a new agent |
| `GET` | `/agents` | List all active agents |

---

## Data Model

```
Commodity ──< PriceEntry >── Market
                  │
                Agent
```

**PriceEntry** links a commodity, market, and agent with a price, currency (default: `ZMW`), unit, and verification status.

**Verification statuses:** `PENDING` → `VERIFIED` | `REJECTED`

**Agent roles:** `ADMIN`, `FIELD_AGENT`

---

## Caching

Redis caches are applied to read-heavy endpoints with TTLs tuned to data volatility:

| Cache | TTL | Evicted on |
|-------|-----|------------|
| Active commodities | 1 hour | New commodity created |
| Active markets | 1 hour | New market created |
| Latest prices | 5 minutes | New price submitted |
| Price comparisons | 10 minutes | New price submitted |

---

## Project Structure

```
src/main/java/com/ephraim/marketpriceapi/
├── config/         # Redis, CORS, OpenAPI configuration
├── controller/     # REST controllers
├── service/        # Business logic interfaces + implementations
├── repository/     # JPA repositories with custom queries
├── entity/         # JPA entities
├── dto/            # Request and response DTOs
├── mapper/         # Entity ↔ DTO mappers
└── exception/      # Custom exceptions + global handler
```

---

## Error Responses

All errors return a structured JSON body:

```json
{
  "timestamp": "2026-03-28T10:00:00",
  "status": 404,
  "error": "Resource not found",
  "path": "/api/v1/prices/latest"
}
```

Custom exceptions: `ResourceNotFoundException`, `DuplicateResourceException`, `BadRequestException`.

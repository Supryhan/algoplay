# Crypto Exchange Implementation Plan

## Experiment metadata

Model: Claude Opus 4.6
Model mode: Thinking
Provider/runtime: Antigravity
Underlying provider: Anthropic
Execution mode: cloud agent / IDE agent
Quantization: not applicable / provider-managed
Task: crypto exchange implementation plan
Date: 2026-06-09
Prompt version: v1
Result type: raw model output
Comparison group: local coding models

Compared against:

* qwen2.5-coder:7b-instruct-q4_K_M
* qwen2.5-coder:14b-instruct-q4_K_M
* qwen3-coder:30b-a3b-q4_K_M

Antigravity resource status before/around run:

* Claude 4.6 Opus (Thinking)
* Reset: 6d 23h
* Resource shown: 80%

Primary evaluation goal: compare high-level implementation planning quality against local coding models
Expected strengths: architecture decomposition, domain modeling, implementation sequencing, risk identification, financial correctness, testing strategy
Expected weaknesses: may be more abstract, verbose, or expensive in quota/resource usage than local models
Hardware: not applicable, cloud inference
Local machine: MacBook M3 Pro, 36 GB unified memory
Notes: this run is intended as an external high-quality baseline for planning, not as a local inference benchmark.


## Original prompt

```text
You are a senior software architect and backend engineer.

I want to build an educational crypto exchange backend project from scratch. The goal is not to build a production-ready financial system, but to create a serious portfolio project that demonstrates backend architecture, domain modeling, trading logic, persistence, testing, Docker, and clean implementation steps.

Target stack:
- Scala
- Cats Effect or ZIO
- HTTP API
- PostgreSQL
- Redis
- Docker and Docker Compose
- sbt
- automated tests

The project should include these core domains:
- users
- accounts and balances
- deposits and withdrawals as simulated operations
- trading pairs
- orders
- order book
- matching engine
- trades
- market data
- admin operations
- audit log

Important constraints:
- Do not generate the full code now.
- Create a detailed implementation plan.
- The plan must be practical and suitable for step-by-step coding.
- Split the work into small milestones.
- Each milestone must have:
  1. goal
  2. files or modules to create
  3. main classes, traits, or functions
  4. database tables if needed
  5. tests to write
  6. expected result after the milestone
- Start with a minimal working skeleton and then evolve it.
- Avoid vague advice.
- Prefer concrete backend engineering tasks.
- Mention where domain logic should live and where infrastructure code should live.
- Keep security and financial correctness in mind, but do not overcomplicate the first version.

Return the answer as a numbered implementation plan with 12 to 18 milestones.
```
# Result:

# Crypto Exchange Backend — Implementation Plan

## Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│                      HTTP Layer                         │
│             (http4s routes + Circe codecs)              │
├─────────────────────────────────────────────────────────┤
│                   Application Services                  │
│        (orchestration, transaction boundaries)          │
├─────────────────────────────────────────────────────────┤
│                     Domain Layer                        │
│   (entities, value objects, pure domain logic, algebras)│
├─────────────────────────────────────────────────────────┤
│                  Infrastructure Layer                   │
│     (Doobie repos, Redis cache, Flyway migrations)      │
├──────────────┬──────────────────┬───────────────────────┤
│  PostgreSQL  │      Redis       │    Docker Compose     │
└──────────────┴──────────────────┴───────────────────────┘
```

**Hexagonal Architecture Rules:**
- **Domain** (`domain/`): Pure Scala. No framework imports. Entities, value objects, enums, domain error ADTs, and algebra trait definitions live here. This is the core — it has zero dependencies on http4s, Doobie, or any infra library.
- **Application** (`application/`): Service classes that orchestrate domain logic. They depend on domain algebra traits (ports), not on concrete implementations. Transaction boundaries are defined here.
- **Infrastructure** (`infrastructure/`): Doobie repository implementations, Redis client wrappers, Flyway migration runner. These implement the domain algebra traits.
- **API** (`api/`): http4s routes, Circe codecs, request/response DTOs, middleware (auth, logging, error handling). Depends on application services.

**Technology Stack:**

| Concern | Library | Version (approx.) |
|---|---|---|
| Effect system | Cats Effect 3 | 3.5.x |
| HTTP server | http4s (Ember) | 0.23.x |
| JSON | Circe | 0.14.x |
| DB access | Doobie | 1.0.x |
| DB migrations | Flyway | 10.x |
| Redis | Redis4Cats | 1.7.x |
| Logging | log4cats + Logback | 2.7.x |
| Config | Ciris | 3.6.x |
| Testing | MUnit + MUnit-CE3 | 1.x |
| Integration tests | Testcontainers-scala | 0.41.x |
| Build tool | sbt | 1.10.x |
| Scala version | Scala 3 | 3.3.x LTS |

**Project layout (final state):**

```
crypto-exchange/
├── build.sbt
├── project/
│   ├── build.properties
│   └── plugins.sbt
├── docker-compose.yml
├── Dockerfile
├── src/
│   ├── main/
│   │   ├── scala/exchange/
│   │   │   ├── Main.scala
│   │   │   ├── config/
│   │   │   │   └── AppConfig.scala
│   │   │   ├── domain/
│   │   │   │   ├── user/
│   │   │   │   ├── account/
│   │   │   │   ├── order/
│   │   │   │   ├── trade/
│   │   │   │   ├── market/
│   │   │   │   └── common/
│   │   │   ├── application/
│   │   │   │   ├── UserService.scala
│   │   │   │   ├── AccountService.scala
│   │   │   │   ├── OrderService.scala
│   │   │   │   ├── TradingService.scala
│   │   │   │   ├── MarketDataService.scala
│   │   │   │   └── AdminService.scala
│   │   │   ├── infrastructure/
│   │   │   │   ├── db/
│   │   │   │   ├── redis/
│   │   │   │   └── migration/
│   │   │   └── api/
│   │   │       ├── routes/
│   │   │       ├── codecs/
│   │   │       ├── middleware/
│   │   │       └── dto/
│   │   └── resources/
│   │       ├── application.conf (or reference.conf)
│   │       ├── logback.xml
│   │       └── db/migration/   (Flyway SQL files)
│   └── test/
│       └── scala/exchange/
│           ├── domain/
│           ├── application/
│           ├── infrastructure/
│           └── api/
└── README.md
```

---

## Open Questions

> [!IMPORTANT]
> **Effect system choice**: This plan uses **Cats Effect 3** (with http4s + Doobie + Circe). If you prefer **ZIO**, the architecture stays the same but the libraries change to zio-http, zio-jdbc/quill, and zio-json. Let me know if you want the ZIO variant.

> [!IMPORTANT]
> **Authentication model**: The plan uses a simple JWT-based auth (no OAuth/SSO). Is that sufficient for a portfolio project, or do you want API key authentication as well?

> [!IMPORTANT]
> **Scala version**: The plan targets **Scala 3.3.x LTS**. If you need Scala 2.13 compatibility (e.g., for employer stack alignment), flag it now — some syntax and library choices change.

---

## Milestones

---

### Milestone 1 — Project Skeleton & Docker Compose

**Goal:** Bootable sbt project with Docker Compose bringing up PostgreSQL and Redis. The app starts, connects to both, and responds to a health-check endpoint.

**Files to create:**

| File | Purpose |
|---|---|
| `build.sbt` | Dependencies, Scala 3 settings, single-module project |
| `project/build.properties` | sbt version |
| `project/plugins.sbt` | sbt-assembly or sbt-native-packager (for Docker later) |
| `docker-compose.yml` | PostgreSQL 16 + Redis 7 services |
| `src/main/scala/exchange/Main.scala` | IOApp entry point |
| `src/main/scala/exchange/config/AppConfig.scala` | Case class for DB/Redis/server config, loaded via Ciris |
| `src/main/scala/exchange/infrastructure/db/DatabaseModule.scala` | Doobie `Transactor[IO]` resource |
| `src/main/scala/exchange/infrastructure/redis/RedisModule.scala` | Redis4Cats `RedisCommands[IO, String, String]` resource |
| `src/main/scala/exchange/infrastructure/migration/FlywayMigrator.scala` | Runs Flyway on startup |
| `src/main/scala/exchange/api/routes/HealthRoutes.scala` | `GET /health` → checks DB + Redis connectivity |
| `src/main/resources/logback.xml` | Logback config |
| `src/main/resources/db/migration/V001__init.sql` | Empty migration (schema placeholder) |

**Key classes/traits/functions:**

```scala
// Main.scala
object Main extends IOApp.Simple:
  def run: IO[Unit] = AppResources.make[IO](config).use { resources =>
    FlywayMigrator.migrate[IO](config.db) *>
    HttpServer.start[IO](config.server, healthRoutes(resources))
  }

// AppConfig.scala
final case class AppConfig(db: DbConfig, redis: RedisConfig, server: ServerConfig)
final case class DbConfig(host: String, port: Int, name: String, user: String, password: String)
final case class RedisConfig(uri: String)
final case class ServerConfig(host: String, port: Int)

// HealthRoutes.scala
def healthRoutes[F[_]: Concurrent](tx: Transactor[F], redis: RedisCommands[F, String, String]): HttpRoutes[F]
```

**Database tables:** None yet (V001 is a placeholder).

**Tests:**
- `HealthRoutesSpec` — unit test using a mock transactor, assert 200 on `GET /health`
- Manual: `docker compose up -d && sbt run`, curl `localhost:8080/health`

**Expected result:** `docker compose up` starts PG + Redis. `sbt run` starts the server. `GET /health` returns `{"status":"ok","db":"connected","redis":"connected"}`.

---

### Milestone 2 — Domain: Users & Password Hashing

**Goal:** Define the `User` domain entity, registration/login application service, and persistence. No HTTP routes yet — prove it works via tests.

**Files to create:**

| File | Purpose |
|---|---|
| `domain/user/User.scala` | `User` entity, `UserId`, `Email`, `HashedPassword` value objects |
| `domain/user/UserRepository.scala` | Algebra trait (port) |
| `domain/common/Errors.scala` | Domain error ADT — `DomainError` sealed trait |
| `application/UserService.scala` | `register`, `login` (returns JWT claims, not token yet) |
| `infrastructure/db/DoobieUserRepository.scala` | Doobie implementation of `UserRepository` |
| `infrastructure/db/Codecs.scala` | Doobie `Meta` / `Read` / `Write` instances for value objects |
| `src/main/resources/db/migration/V002__users.sql` | `users` table |

**Key classes/traits/functions:**

```scala
// domain/user/User.scala
final case class UserId(value: UUID)
final case class Email(value: String)        // smart constructor with validation
final case class HashedPassword(value: String)
enum UserRole { case Customer, Admin }

final case class User(
  id: UserId, email: Email, hashedPassword: HashedPassword,
  role: UserRole, createdAt: Instant, updatedAt: Instant
)

// domain/user/UserRepository.scala
trait UserRepository[F[_]]:
  def create(user: User): F[Unit]
  def findById(id: UserId): F[Option[User]]
  def findByEmail(email: Email): F[Option[User]]
  def existsByEmail(email: Email): F[Boolean]

// application/UserService.scala
class UserService[F[_]: Sync](repo: UserRepository[F], hasher: PasswordHasher[F]):
  def register(email: Email, rawPassword: String): F[Either[DomainError, User]]
  def authenticate(email: Email, rawPassword: String): F[Either[DomainError, User]]
```

**Database tables:**

```sql
-- V002__users.sql
CREATE TABLE users (
  id         UUID PRIMARY KEY,
  email      VARCHAR(255) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  role       VARCHAR(20)  NOT NULL DEFAULT 'customer',
  created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);
CREATE INDEX idx_users_email ON users (email);
```

**Tests:**
- `UserSpec` — unit test: smart constructor rejects invalid emails
- `UserServiceSpec` — unit test with in-memory `UserRepository` stub: registration succeeds, duplicate email fails, login with wrong password fails
- `DoobieUserRepositorySpec` — integration test with Testcontainers PG: insert + findByEmail round-trip

**Expected result:** `UserService.register` creates a user with a bcrypt-hashed password. `UserService.authenticate` verifies credentials. All tests green.

---

### Milestone 3 — JWT Auth & User API Routes

**Goal:** HTTP endpoints for registration and login. JWT middleware protects subsequent routes. This is the first "curl-able" feature beyond health.

**Files to create:**

| File | Purpose |
|---|---|
| `infrastructure/auth/JwtService.scala` | Create & validate JWT tokens (using `jwt-scala` or `tsec`) |
| `api/middleware/AuthMiddleware.scala` | Extract JWT from `Authorization: Bearer ...`, attach `AuthUser` to request |
| `api/dto/UserDto.scala` | `RegisterRequest`, `LoginRequest`, `LoginResponse`, `UserResponse` |
| `api/codecs/UserCodecs.scala` | Circe `Encoder` / `Decoder` for DTOs |
| `api/routes/UserRoutes.scala` | `POST /api/users/register`, `POST /api/users/login`, `GET /api/users/me` |
| `api/middleware/ErrorHandler.scala` | Map `DomainError` → appropriate HTTP status |

**Key classes/traits/functions:**

```scala
// infrastructure/auth/JwtService.scala
trait JwtService[F[_]]:
  def createToken(user: User): F[JwtToken]
  def validateToken(token: String): F[Either[AuthError, AuthUser]]

final case class AuthUser(userId: UserId, email: Email, role: UserRole)
final case class JwtToken(value: String, expiresAt: Instant)

// api/middleware/AuthMiddleware.scala
def authMiddleware[F[_]: Async](jwt: JwtService[F]): AuthMiddleware[F, AuthUser]

// api/routes/UserRoutes.scala
class UserRoutes[F[_]: Async](userService: UserService[F], jwt: JwtService[F]):
  val publicRoutes: HttpRoutes[F]    // register, login
  val authedRoutes: AuthedRoutes[F, AuthUser]  // GET /me
```

**Database tables:** None (JWT is stateless).

**Tests:**
- `UserRoutesSpec` — unit test: register returns 201, login returns 200 with token, `/me` without token returns 401
- `AuthMiddlewareSpec` — expired token → 401, malformed token → 401, valid token → passes `AuthUser`

**Expected result:** Full registration → login → access-protected-endpoint flow works via curl.

---

### Milestone 4 — Domain: Currencies, Accounts & Balances

**Goal:** Introduce the `Currency` value object, `Account` entity (one account per user per currency), and balance operations with domain-level correctness (no negative balances).

**Files to create:**

| File | Purpose |
|---|---|
| `domain/account/Currency.scala` | `Currency` enum or newtype (`BTC`, `ETH`, `USDT`, etc.) |
| `domain/account/Account.scala` | `Account` entity, `AccountId`, `Balance` (available + held) |
| `domain/account/AccountRepository.scala` | Algebra trait |
| `application/AccountService.scala` | `getBalances`, `getBalance`, internal `credit` / `debit` / `hold` / `release` |
| `infrastructure/db/DoobieAccountRepository.scala` | Doobie impl with `SELECT ... FOR UPDATE` for balance ops |
| `api/routes/AccountRoutes.scala` | `GET /api/accounts`, `GET /api/accounts/:currency` |
| `api/dto/AccountDto.scala` | Response DTOs |
| `src/main/resources/db/migration/V003__accounts.sql` | `accounts` table |

**Key classes/traits/functions:**

```scala
// domain/account/Account.scala
final case class AccountId(value: UUID)

final case class Account(
  id: AccountId, userId: UserId, currency: Currency,
  available: BigDecimal, held: BigDecimal,
  createdAt: Instant, updatedAt: Instant
):
  def total: BigDecimal = available + held

  /** Pure domain logic — returns Either, no effects */
  def holdFunds(amount: BigDecimal): Either[DomainError, Account] =
    if amount <= BigDecimal(0) then Left(InvalidAmount)
    else if available < amount then Left(InsufficientFunds)
    else Right(copy(available = available - amount, held = held + amount))

  def releaseFunds(amount: BigDecimal): Either[DomainError, Account]
  def debit(amount: BigDecimal): Either[DomainError, Account]
  def credit(amount: BigDecimal): Either[DomainError, Account]

// domain/account/AccountRepository.scala
trait AccountRepository[F[_]]:
  def create(account: Account): F[Unit]
  def findByUserAndCurrency(userId: UserId, currency: Currency): F[Option[Account]]
  def findAllByUser(userId: UserId): F[List[Account]]
  def update(account: Account): F[Unit]
  def findByIdForUpdate(id: AccountId): ConnectionIO[Option[Account]]  // row-lock
```

> [!IMPORTANT]
> `holdFunds`, `releaseFunds`, `debit`, `credit` are **pure functions** on the `Account` case class — they return `Either[DomainError, Account]`. The application service is responsible for loading the entity, calling the domain function, and persisting the result inside a DB transaction. This keeps domain logic testable without a database.

**Database tables:**

```sql
-- V003__accounts.sql
CREATE TABLE accounts (
  id         UUID PRIMARY KEY,
  user_id    UUID NOT NULL REFERENCES users(id),
  currency   VARCHAR(10) NOT NULL,
  available  NUMERIC(28,18) NOT NULL DEFAULT 0 CHECK (available >= 0),
  held       NUMERIC(28,18) NOT NULL DEFAULT 0 CHECK (held >= 0),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  UNIQUE (user_id, currency)
);
CREATE INDEX idx_accounts_user ON accounts (user_id);
```

**Tests:**
- `AccountSpec` — unit tests for `holdFunds` / `releaseFunds` / `debit` / `credit` pure logic (15+ cases: zero amount, negative amount, insufficient, exact amount, etc.)
- `AccountServiceSpec` — unit test with stubbed repo: get balances for user
- `DoobieAccountRepositorySpec` — integration: create, findByUserAndCurrency, update round-trip

**Expected result:** Accounts are created during registration (one per supported currency). `GET /api/accounts` returns balances (all zero initially). Domain logic enforces no negative balances at the type level.

---

### Milestone 5 — Deposits & Withdrawals (Simulated)

**Goal:** Simulate fiat/crypto deposits and withdrawals. No real blockchain interaction — just credit/debit with an audit trail. This exercises transactional account operations.

**Files to create:**

| File | Purpose |
|---|---|
| `domain/account/Transfer.scala` | `Deposit` and `Withdrawal` entities, `TransferStatus` enum |
| `domain/account/TransferRepository.scala` | Algebra trait |
| `application/TransferService.scala` | `deposit`, `withdraw` — orchestrates account credit/debit in a transaction |
| `infrastructure/db/DoobieTransferRepository.scala` | Doobie impl |
| `api/routes/TransferRoutes.scala` | `POST /api/deposits`, `POST /api/withdrawals`, `GET /api/transfers` |
| `api/dto/TransferDto.scala` | Request/response DTOs |
| `src/main/resources/db/migration/V004__transfers.sql` | `transfers` table |

**Key classes/traits/functions:**

```scala
// domain/account/Transfer.scala
enum TransferType { case Deposit, Withdrawal }
enum TransferStatus { case Pending, Completed, Failed }

final case class Transfer(
  id: TransferId, userId: UserId, currency: Currency,
  amount: BigDecimal, transferType: TransferType,
  status: TransferStatus,
  createdAt: Instant, completedAt: Option[Instant]
)

// application/TransferService.scala
class TransferService[F[_]: Sync](
  transferRepo: TransferRepository[F],
  accountRepo: AccountRepository[F],
  transactor: Transactor[F]
):
  /** Runs account.credit + transfer insert inside a single DB transaction */
  def deposit(userId: UserId, currency: Currency, amount: BigDecimal): F[Either[DomainError, Transfer]]
  def withdraw(userId: UserId, currency: Currency, amount: BigDecimal): F[Either[DomainError, Transfer]]
  def getHistory(userId: UserId, page: Int, limit: Int): F[List[Transfer]]
```

**Database tables:**

```sql
-- V004__transfers.sql
CREATE TABLE transfers (
  id            UUID PRIMARY KEY,
  user_id       UUID NOT NULL REFERENCES users(id),
  currency      VARCHAR(10) NOT NULL,
  amount        NUMERIC(28,18) NOT NULL CHECK (amount > 0),
  transfer_type VARCHAR(20) NOT NULL,  -- 'deposit' | 'withdrawal'
  status        VARCHAR(20) NOT NULL DEFAULT 'completed',
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  completed_at  TIMESTAMPTZ
);
CREATE INDEX idx_transfers_user ON transfers (user_id);
```

**Tests:**
- `TransferServiceSpec` — unit test with stubs: deposit increases available balance, withdrawal decreases it, withdrawal of more than available fails, double-deposit sums correctly
- `TransferRoutesSpec` — HTTP: authenticated deposit → 201, unauthenticated → 401
- `DoobieTransferRepositorySpec` — integration: insert + query by user

**Expected result:** A user can deposit USDT, see their balance increase, then withdraw. The transfer history is queryable with pagination.

---

### Milestone 6 — Trading Pairs & Order Domain Model

**Goal:** Define trading pairs (e.g., `BTC/USDT`) and the `Order` domain model. No matching yet — orders are just placed and stored. This milestone focuses on getting the domain model right.

**Files to create:**

| File | Purpose |
|---|---|
| `domain/market/TradingPair.scala` | `TradingPair` entity, `TradingPairId` |
| `domain/market/TradingPairRepository.scala` | Algebra trait |
| `domain/order/Order.scala` | `Order` entity with full lifecycle states |
| `domain/order/OrderRepository.scala` | Algebra trait |
| `domain/order/OrderValidator.scala` | Pure validation: min qty, tick size, self-trade check |
| `application/OrderService.scala` | `placeOrder`, `cancelOrder`, `getUserOrders` (no matching yet) |
| `infrastructure/db/DoobieTradingPairRepository.scala` | Doobie impl |
| `infrastructure/db/DoobieOrderRepository.scala` | Doobie impl |
| `api/routes/OrderRoutes.scala` | `POST /api/orders`, `DELETE /api/orders/:id`, `GET /api/orders` |
| `api/routes/MarketRoutes.scala` | `GET /api/markets` (list trading pairs) |
| `src/main/resources/db/migration/V005__trading_pairs.sql` | `trading_pairs` table |
| `src/main/resources/db/migration/V006__orders.sql` | `orders` table |

**Key classes/traits/functions:**

```scala
// domain/market/TradingPair.scala
final case class TradingPairId(value: String)  // "BTC_USDT"
final case class TradingPair(
  id: TradingPairId, baseCurrency: Currency, quoteCurrency: Currency,
  minQuantity: BigDecimal, tickSize: BigDecimal,
  isActive: Boolean
)

// domain/order/Order.scala
enum OrderSide { case Buy, Sell }
enum OrderType { case Limit, Market }
enum OrderStatus { case Open, PartiallyFilled, Filled, Cancelled }

final case class OrderId(value: UUID)

final case class Order(
  id: OrderId, userId: UserId, pair: TradingPairId,
  side: OrderSide, orderType: OrderType,
  price: Option[BigDecimal],   // None for market orders
  quantity: BigDecimal,         // original quantity
  filled: BigDecimal,           // how much has been filled
  status: OrderStatus,
  createdAt: Instant, updatedAt: Instant
):
  def remainingQuantity: BigDecimal = quantity - filled
  def isFillable: Boolean = status == OrderStatus.Open || status == OrderStatus.PartiallyFilled

// domain/order/OrderValidator.scala
object OrderValidator:
  def validate(order: Order, pair: TradingPair): Either[DomainError, Order]
  // checks: quantity >= minQuantity, price aligns with tickSize, limit orders must have price, etc.
```

**Database tables:**

```sql
-- V005__trading_pairs.sql
CREATE TABLE trading_pairs (
  id             VARCHAR(20) PRIMARY KEY,  -- 'BTC_USDT'
  base_currency  VARCHAR(10) NOT NULL,
  quote_currency VARCHAR(10) NOT NULL,
  min_quantity   NUMERIC(28,18) NOT NULL,
  tick_size      NUMERIC(28,18) NOT NULL,
  is_active      BOOLEAN NOT NULL DEFAULT true,
  UNIQUE (base_currency, quote_currency)
);

INSERT INTO trading_pairs (id, base_currency, quote_currency, min_quantity, tick_size)
VALUES
  ('BTC_USDT', 'BTC', 'USDT', 0.0001, 0.01),
  ('ETH_USDT', 'ETH', 'USDT', 0.001, 0.01),
  ('ETH_BTC', 'ETH', 'BTC', 0.001, 0.000001);

-- V006__orders.sql
CREATE TABLE orders (
  id         UUID PRIMARY KEY,
  user_id    UUID NOT NULL REFERENCES users(id),
  pair_id    VARCHAR(20) NOT NULL REFERENCES trading_pairs(id),
  side       VARCHAR(4) NOT NULL,   -- 'buy' | 'sell'
  order_type VARCHAR(10) NOT NULL,  -- 'limit' | 'market'
  price      NUMERIC(28,18),        -- nullable for market orders
  quantity   NUMERIC(28,18) NOT NULL CHECK (quantity > 0),
  filled     NUMERIC(28,18) NOT NULL DEFAULT 0,
  status     VARCHAR(20) NOT NULL DEFAULT 'open',
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX idx_orders_user ON orders (user_id);
CREATE INDEX idx_orders_pair_status ON orders (pair_id, status);
CREATE INDEX idx_orders_pair_side_price ON orders (pair_id, side, price);
```

**Tests:**
- `OrderValidatorSpec` — exhaustive pure tests: invalid quantity, missing price on limit, tick alignment
- `OrderServiceSpec` — unit test: place order holds funds, cancel order releases funds, order for inactive pair fails
- `TradingPairRepositorySpec` — integration: seed data loads, findById works

**Expected result:** Users can place limit/buy orders. Funds are held on placement. Orders can be cancelled (funds released). No matching occurs yet — orders sit in `Open` status.

---

### Milestone 7 — In-Memory Order Book

**Goal:** Build the core order book data structure as a pure, in-memory, immutable structure. No database persistence of the book itself — it is reconstructed from open orders on startup. This is the intellectual heart of the project.

**Files to create:**

| File | Purpose |
|---|---|
| `domain/order/OrderBook.scala` | Immutable order book: sorted bid/ask sides |
| `domain/order/OrderBookSide.scala` | One side (bids or asks) — a sorted collection of price levels |
| `domain/order/PriceLevel.scala` | Orders queued at a single price (FIFO) |

**Key classes/traits/functions:**

```scala
// domain/order/PriceLevel.scala
final case class PriceLevel(price: BigDecimal, orders: Queue[Order]):
  def addOrder(order: Order): PriceLevel
  def removeOrder(orderId: OrderId): PriceLevel
  def headOption: Option[Order]
  def totalQuantity: BigDecimal
  def isEmpty: Boolean

// domain/order/OrderBook.scala
final case class OrderBook(pair: TradingPairId, bids: OrderBookSide, asks: OrderBookSide):
  def addOrder(order: Order): OrderBook
  def removeOrder(orderId: OrderId, side: OrderSide): OrderBook
  def bestBid: Option[BigDecimal]
  def bestAsk: Option[BigDecimal]
  def spread: Option[BigDecimal]
  def depth(side: OrderSide, levels: Int): List[(BigDecimal, BigDecimal)]  // (price, totalQty)

object OrderBook:
  def empty(pair: TradingPairId): OrderBook
  def fromOrders(pair: TradingPairId, orders: List[Order]): OrderBook
```

> [!IMPORTANT]
> The `OrderBook` is a **pure immutable data structure**. It has no side effects, no `F[_]`, and no database dependency. This makes it trivially testable and easy to reason about. The matching engine (next milestone) will operate on it and produce a list of resulting events.

**Database tables:** None — the order book is ephemeral. It is reconstructed from the `orders` table on startup.

**Tests:**
- `OrderBookSpec` — extensive property-based and example-based tests:
    - Add buy order → appears in bids at correct level
    - Add sell order → appears in asks
    - Best bid is highest price, best ask is lowest price
    - Spread computation
    - Remove order works correctly
    - FIFO ordering within a price level
    - `depth(Buy, 5)` returns top 5 bid levels aggregated
    - `fromOrders` reconstruction matches manual adds

**Expected result:** A pure, well-tested `OrderBook` data structure ready for the matching engine.

---

### Milestone 8 — Matching Engine

**Goal:** Implement the matching engine as a pure function that takes an incoming order and the current order book, and returns a `MatchResult` containing: the list of trades generated, the updated order book, and the updated states of all affected orders.

**Files to create:**

| File | Purpose |
|---|---|
| `domain/order/MatchingEngine.scala` | Pure matching logic |
| `domain/trade/Trade.scala` | `Trade` entity |
| `domain/order/MatchResult.scala` | Result ADT: trades + updated book + order state changes |

**Key classes/traits/functions:**

```scala
// domain/trade/Trade.scala
final case class TradeId(value: UUID)
final case class Trade(
  id: TradeId,
  pair: TradingPairId,
  buyOrderId: OrderId, sellOrderId: OrderId,
  buyerUserId: UserId, sellerUserId: UserId,
  price: BigDecimal,   // execution price (maker's price)
  quantity: BigDecimal, // matched quantity
  createdAt: Instant
)

// domain/order/MatchResult.scala
final case class MatchResult(
  trades: List[Trade],
  updatedBook: OrderBook,
  makerUpdates: List[Order],   // resting orders that got (partially) filled
  takerUpdate: Order           // the incoming order's final state
)

// domain/order/MatchingEngine.scala
object MatchingEngine:
  /**
   * Pure function. Takes the incoming (taker) order and the current book.
   * Returns all trades plus the new book state.
   *
   * Rules:
   * - Limit buy: matches against asks where ask.price <= buy.price (price-time priority)
   * - Limit sell: matches against bids where bid.price >= sell.price
   * - Market buy: matches against asks at any price (best first)
   * - Market sell: matches against bids at any price (best first)
   * - Trade executes at the maker's (resting) price
   * - Partial fills are allowed
   * - Unmatched remainder of a limit order rests on the book
   * - Unmatched remainder of a market order is cancelled
   */
  def matchOrder(order: Order, book: OrderBook, now: Instant): MatchResult
```

**Database tables:** None — matching is pure logic.

**Tests (this is the most critical test suite):**
- `MatchingEngineSpec`:
    - Limit buy at ask price → full fill, one trade
    - Limit buy above ask → fills at ask price (maker price)
    - Limit buy below best ask → no match, rests on book
    - Limit sell at bid price → full fill
    - Partial fill: buy 10 BTC but only 3 available → trade for 3, remainder rests
    - Multiple fills across price levels: buy sweeps 3 ask levels
    - Market buy → fills at best available prices, unfilled remainder cancelled
    - Market sell with empty bids → order cancelled, no trades
    - Self-trade prevention (optional — can defer to Milestone 14)
    - Price-time priority: two asks at same price, older one fills first

**Expected result:** `MatchingEngine.matchOrder` is a thoroughly tested pure function. No I/O, no state mutation. This is the function that future milestones will wire into the application layer.

---

### Milestone 9 — Trading Service (Wiring It Together)

**Goal:** Wire the matching engine into the application layer. When a user places an order, the system holds their funds, runs the matching engine, persists resulting trades, updates all order statuses, and settles balances — all within a single database transaction.

**Files to create:**

| File | Purpose |
|---|---|
| `domain/trade/TradeRepository.scala` | Algebra trait |
| `application/TradingService.scala` | Orchestrates the full place-order flow |
| `infrastructure/db/DoobieTradeRepository.scala` | Doobie impl |
| `src/main/resources/db/migration/V007__trades.sql` | `trades` table |

**Key classes/traits/functions:**

```scala
// application/TradingService.scala
class TradingService[F[_]: Sync](
  orderRepo: OrderRepository[F],
  tradeRepo: TradeRepository[F],
  accountRepo: AccountRepository[F],
  pairRepo: TradingPairRepository[F],
  orderBooks: Ref[F, Map[TradingPairId, OrderBook]],  // in-memory book state
  transactor: Transactor[F]
):
  /**
   * Full flow:
   * 1. Validate order against trading pair rules
   * 2. Hold funds (quote currency for buy, base currency for sell)
   * 3. Run MatchingEngine.matchOrder against current book
   * 4. For each trade: settle balances (credit buyer base, credit seller quote)
   * 5. Persist: insert order, update matched orders, insert trades, update accounts
   * 6. Update in-memory OrderBook via Ref
   * All DB operations in a single transaction.
   */
  def placeOrder(userId: UserId, request: PlaceOrderRequest): F[Either[DomainError, PlaceOrderResult]]

  def cancelOrder(userId: UserId, orderId: OrderId): F[Either[DomainError, Order]]

  /** Reconstruct in-memory order books from DB on startup */
  def initialize: F[Unit]

final case class PlaceOrderResult(order: Order, trades: List[Trade])
```

> [!IMPORTANT]
> **Concurrency model:** Order books are held in a `cats.effect.Ref[F, Map[TradingPairId, OrderBook]]`. For an educational project, `Ref` with its compare-and-swap semantics is sufficient. For production you'd want a dedicated fiber per trading pair processing a `Queue[F, OrderCommand]`, but that's out of scope for v1.

**Database tables:**

```sql
-- V007__trades.sql
CREATE TABLE trades (
  id             UUID PRIMARY KEY,
  pair_id        VARCHAR(20) NOT NULL REFERENCES trading_pairs(id),
  buy_order_id   UUID NOT NULL REFERENCES orders(id),
  sell_order_id  UUID NOT NULL REFERENCES orders(id),
  buyer_user_id  UUID NOT NULL REFERENCES users(id),
  seller_user_id UUID NOT NULL REFERENCES users(id),
  price          NUMERIC(28,18) NOT NULL,
  quantity       NUMERIC(28,18) NOT NULL,
  created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX idx_trades_pair ON trades (pair_id, created_at DESC);
CREATE INDEX idx_trades_buyer ON trades (buyer_user_id);
CREATE INDEX idx_trades_seller ON trades (seller_user_id);
```

**Tests:**
- `TradingServiceSpec` — integration test (Testcontainers):
    - User A deposits 10,000 USDT. User B deposits 1 BTC.
    - User B places sell 1 BTC @ 30,000 USDT
    - User A places buy 0.5 BTC @ 30,000 USDT
    - Assert: 1 trade generated, User A has 0.5 BTC + 5,000 USDT remaining, User B has 0.5 BTC remaining + 15,000 USDT, order statuses correct
- `TradingServiceSpec` — cancel order releases held funds
- `TradingServiceSpec` — order for insufficient funds returns error

**Expected result:** The complete order → match → trade → settlement flow works end-to-end. This is the core milestone of the project.

---

### Milestone 10 — Trade & Order API Routes

**Goal:** Expose trading endpoints and user-facing trade/order history. Add the order book depth endpoint.

**Files to create:**

| File | Purpose |
|---|---|
| `api/routes/TradingRoutes.scala` | Full trading API |
| `api/dto/TradingDto.scala` | Request/response DTOs for orders, trades, depth |
| `api/codecs/TradingCodecs.scala` | Circe codecs |

**Key endpoints:**

```
POST   /api/orders                 — place order (auth required)
DELETE /api/orders/:id             — cancel order (auth required)
GET    /api/orders                 — user's open orders (auth, paginated)
GET    /api/orders/history         — user's order history (auth, paginated)
GET    /api/trades                 — user's trade history (auth, paginated)
GET    /api/markets/:pair/depth    — order book depth (public, returns top N levels)
GET    /api/markets/:pair/trades   — recent trades for a pair (public)
```

**Database tables:** None (uses existing tables).

**Tests:**
- `TradingRoutesSpec` — full HTTP-level test:
    - Register two users, deposit, place orders, verify trade response
    - Depth endpoint returns correct bid/ask levels
    - Unauthenticated access to protected routes → 401
    - Invalid order → 422 with descriptive error

**Expected result:** The exchange is fully usable via HTTP. Two users can register, deposit, trade against each other, and query their history.

---

### Milestone 11 — Market Data & Redis Caching

**Goal:** Compute and cache market data: last price, 24h volume, 24h high/low, price change percentage. Serve it from Redis for fast reads.

**Files to create:**

| File | Purpose |
|---|---|
| `domain/market/MarketData.scala` | `Ticker` entity |
| `domain/market/MarketDataRepository.scala` | Algebra trait for ticker persistence/cache |
| `application/MarketDataService.scala` | Compute tickers, cache to Redis |
| `infrastructure/redis/RedisMarketDataRepository.scala` | Redis impl — store tickers as JSON |
| `api/routes/MarketDataRoutes.scala` | `GET /api/tickers`, `GET /api/tickers/:pair` |
| `api/dto/MarketDataDto.scala` | Response DTOs |

**Key classes/traits/functions:**

```scala
// domain/market/MarketData.scala
final case class Ticker(
  pair: TradingPairId,
  lastPrice: BigDecimal,
  bestBid: Option[BigDecimal],
  bestAsk: Option[BigDecimal],
  high24h: BigDecimal,
  low24h: BigDecimal,
  volume24h: BigDecimal,        // in base currency
  quoteVolume24h: BigDecimal,   // in quote currency
  priceChange24h: BigDecimal,   // absolute
  priceChangePercent24h: BigDecimal,
  updatedAt: Instant
)

// application/MarketDataService.scala
class MarketDataService[F[_]: Sync](
  tradeRepo: TradeRepository[F],
  orderBooks: Ref[F, Map[TradingPairId, OrderBook]],
  cache: MarketDataRepository[F]
):
  /** Recompute all tickers from trade history and current book state */
  def refreshTickers: F[Unit]

  /** Read from cache (Redis), fall back to recompute */
  def getTicker(pair: TradingPairId): F[Option[Ticker]]
  def getAllTickers: F[List[Ticker]]
```

**Database tables:** None — tickers are computed from `trades` and cached in Redis.

**Redis keys:**
- `ticker:{pair_id}` → JSON-encoded `Ticker`, TTL 10 seconds
- `tickers:all` → JSON-encoded `List[Ticker]`, TTL 10 seconds

**Tests:**
- `MarketDataServiceSpec` — unit test: given a set of trades in the last 24h, compute correct high/low/volume/change
- `RedisMarketDataRepositorySpec` — integration test with Testcontainers Redis: write + read ticker, TTL expiry
- `MarketDataRoutesSpec` — HTTP: `GET /api/tickers/BTC_USDT` returns correct JSON shape

**Expected result:** `GET /api/tickers` returns live market data for all pairs. Data is cached in Redis and refreshed periodically (triggered after each trade and/or on a background schedule).

---

### Milestone 12 — Background Ticker Refresh (Fiber-based Scheduler)

**Goal:** Run a background fiber that periodically recomputes and caches market data. Also update tickers reactively after each trade.

**Files to create:**

| File | Purpose |
|---|---|
| `infrastructure/scheduler/TickerRefreshJob.scala` | Background fiber that calls `MarketDataService.refreshTickers` every N seconds |
| Update `TradingService.scala` | After persisting trades, trigger a ticker refresh |
| Update `Main.scala` | Launch the background fiber as part of the application resource lifecycle |

**Key classes/traits/functions:**

```scala
// infrastructure/scheduler/TickerRefreshJob.scala
object TickerRefreshJob:
  def start[F[_]: Temporal](
    marketDataService: MarketDataService[F],
    interval: FiniteDuration
  ): Resource[F, Unit] =
    // Stream.awakeEvery(interval).evalMap(_ => marketDataService.refreshTickers).compile.drain
    // Run as a background fiber via Resource.make + fiber.start
```

**Tests:**
- `TickerRefreshJobSpec` — integration: start job, place a trade, wait for refresh interval, verify ticker updated in cache

**Expected result:** Market data stays fresh automatically. No manual refresh needed.

---

### Milestone 13 — Audit Log

**Goal:** Record every significant domain event (registration, deposit, withdrawal, order placed/cancelled/filled, trade executed) into an append-only audit log.

**Files to create:**

| File | Purpose |
|---|---|
| `domain/common/AuditEvent.scala` | `AuditEvent` entity, `AuditAction` enum |
| `domain/common/AuditRepository.scala` | Algebra trait |
| `application/AuditService.scala` | Record events, query audit log |
| `infrastructure/db/DoobieAuditRepository.scala` | Doobie impl (append-only) |
| `api/routes/AdminRoutes.scala` | `GET /api/admin/audit` (admin-only) |
| `src/main/resources/db/migration/V008__audit_log.sql` | `audit_events` table |

**Key classes/traits/functions:**

```scala
// domain/common/AuditEvent.scala
enum AuditAction:
  case UserRegistered, UserLoggedIn
  case DepositCompleted, WithdrawalCompleted
  case OrderPlaced, OrderCancelled, OrderFilled
  case TradeExecuted
  case AdminAction

final case class AuditEvent(
  id: UUID,
  userId: Option[UserId],
  action: AuditAction,
  entityType: String,      // "order", "trade", "account"
  entityId: String,        // UUID of the affected entity
  metadata: Json,          // arbitrary JSON payload with details
  ipAddress: Option[String],
  createdAt: Instant
)

// application/AuditService.scala
class AuditService[F[_]: Sync](repo: AuditRepository[F]):
  def record(event: AuditEvent): F[Unit]
  def query(filters: AuditFilters, page: Int, limit: Int): F[List[AuditEvent]]
```

**Integration approach:** Add `AuditService.record(...)` calls into `UserService`, `TransferService`, and `TradingService` at the appropriate points. Use a helper method to keep it clean:

```scala
// In TradingService, after a successful match:
_ <- auditService.record(AuditEvent.orderPlaced(userId, order))
_ <- trades.traverse_(t => auditService.record(AuditEvent.tradeExecuted(t)))
```

**Database tables:**

```sql
-- V008__audit_log.sql
CREATE TABLE audit_events (
  id          UUID PRIMARY KEY,
  user_id     UUID REFERENCES users(id),
  action      VARCHAR(50) NOT NULL,
  entity_type VARCHAR(50) NOT NULL,
  entity_id   VARCHAR(100) NOT NULL,
  metadata    JSONB NOT NULL DEFAULT '{}',
  ip_address  INET,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX idx_audit_user ON audit_events (user_id, created_at DESC);
CREATE INDEX idx_audit_action ON audit_events (action, created_at DESC);
CREATE INDEX idx_audit_entity ON audit_events (entity_type, entity_id);
```

**Tests:**
- `AuditServiceSpec` — unit test: recording events with stubs
- Integration: place an order → verify audit events for `OrderPlaced` + `TradeExecuted` exist in DB
- `AdminRoutesSpec` — only admin users can access `GET /api/admin/audit`, customers get 403

**Expected result:** Every user action is auditable. Admins can query the full audit trail with filters.

---

### Milestone 14 — Admin Operations

**Goal:** Admin-only endpoints for managing the exchange: view all users, toggle trading pair status, view system-wide order book, view aggregate statistics.

**Files to create:**

| File | Purpose |
|---|---|
| `application/AdminService.scala` | Admin operations |
| `api/routes/AdminRoutes.scala` | Admin API (expand from Milestone 13) |
| `api/middleware/RoleMiddleware.scala` | Restrict routes to `Admin` role |

**Key endpoints:**

```
GET    /api/admin/users              — list all users (paginated)
GET    /api/admin/users/:id          — user detail + balances
PATCH  /api/admin/markets/:pair      — enable/disable trading pair
GET    /api/admin/orders             — all open orders system-wide
GET    /api/admin/stats              — aggregate stats (total users, 24h volume, total trades)
GET    /api/admin/audit              — audit log (from Milestone 13)
```

**Key classes/traits/functions:**

```scala
// api/middleware/RoleMiddleware.scala
def adminOnly[F[_]: Monad]: AuthMiddleware[F, AuthUser] =
  // Wraps the standard auth middleware, additionally checks user.role == Admin

// application/AdminService.scala
class AdminService[F[_]: Sync](
  userRepo: UserRepository[F],
  accountRepo: AccountRepository[F],
  pairRepo: TradingPairRepository[F],
  orderRepo: OrderRepository[F],
  tradeRepo: TradeRepository[F]
):
  def listUsers(page: Int, limit: Int): F[PaginatedResult[User]]
  def getUserDetail(id: UserId): F[Option[(User, List[Account])]]
  def togglePair(pairId: TradingPairId, active: Boolean): F[Either[DomainError, TradingPair]]
  def getSystemStats: F[SystemStats]

final case class SystemStats(
  totalUsers: Long, totalTrades: Long, total24hVolume: Map[TradingPairId, BigDecimal]
)
```

**Database tables:** None — uses existing tables.

**Tests:**
- `AdminRoutesSpec` — customer user → 403, admin user → 200
- `AdminServiceSpec` — unit test: toggle pair, get stats
- Integration: disable a trading pair → placing an order on it fails

**Expected result:** Admins have full visibility and control over the exchange.

---

### Milestone 15 — Dockerized Application

**Goal:** Containerize the Scala application. Full `docker compose up` starts PG, Redis, runs migrations, and starts the exchange API — zero manual steps.

**Files to create:**

| File | Purpose |
|---|---|
| `Dockerfile` | Multi-stage build: sbt compile stage → JRE runtime stage |
| Update `docker-compose.yml` | Add `exchange-api` service, depends on PG + Redis |
| `.dockerignore` | Exclude target/, .git, etc. |
| `docker-compose.override.yml` | Dev overrides (port mapping, volume mounts) |

**Dockerfile structure:**

```dockerfile
# Stage 1: Build
FROM sbt:eclipse-temurin-21 AS builder
WORKDIR /app
COPY build.sbt project/ ./project/
COPY src/ ./src/
RUN sbt assembly

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/scala-3.3.x/exchange-assembly-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Tests:**
- Smoke test script: `docker compose up -d && sleep 10 && curl localhost:8080/health && docker compose down`
- Document the full startup in README

**Expected result:** `docker compose up` brings up the entire exchange. A reviewer can clone the repo and have it running in under 2 minutes.

---

### Milestone 16 — Integration Test Suite & Documentation

**Goal:** Comprehensive end-to-end integration test suite that exercises the full exchange lifecycle. Polish the README with architecture docs, API reference, and setup instructions.

**Files to create:**

| File | Purpose |
|---|---|
| `src/test/scala/exchange/ExchangeIntegrationSpec.scala` | Full lifecycle test |
| `README.md` | Project documentation |
| `docs/api.md` | API endpoint reference |
| `docs/architecture.md` | Architecture description with diagrams |

**Integration test scenario:**

```scala
// ExchangeIntegrationSpec.scala — uses Testcontainers for PG + Redis
test("full exchange lifecycle") {
  // 1. Register Alice and Bob
  // 2. Alice deposits 50,000 USDT
  // 3. Bob deposits 2 BTC
  // 4. Bob places sell limit: 1 BTC @ 30,000 USDT
  // 5. Alice places buy limit: 0.5 BTC @ 30,000 USDT
  //    → Assert: 1 trade, Alice has 0.5 BTC + 35,000 USDT, Bob has 1.5 BTC + 15,000 USDT
  //    → Assert: Bob's sell order is PartiallyFilled (0.5 remaining)
  // 6. Alice places buy market: 0.5 BTC
  //    → Assert: fills against Bob's remaining, Bob's order is Filled
  // 7. Verify trade history for both users
  // 8. Verify ticker: last price 30,000, volume 1 BTC
  // 9. Verify audit log entries
  // 10. Alice withdraws 1 BTC → balance updated
  // 11. Admin disables BTC_USDT pair
  // 12. Alice tries to place order → fails with PairDisabled error
  // 13. Verify all balances sum to zero (conservation of value)
}
```

**README structure:**
- Project overview & goals (portfolio project disclaimer)
- Architecture diagram (mermaid)
- Tech stack table
- Getting started (`docker compose up`)
- API reference (link to `docs/api.md`)
- Running tests
- Project structure explanation
- Design decisions (why Cats Effect, why hexagonal, why pure matching engine)

**Tests:**
- The integration test itself is the deliverable
- Run the full test suite: `sbt test`

**Expected result:** The project is complete, documented, and portfolio-ready. Any reviewer can understand the architecture, run it locally, and verify correctness through the test suite.

---

## Verification Plan

### Automated Tests (per milestone)
```bash
# Unit tests (fast, no containers)
sbt "testOnly exchange.domain.*"
sbt "testOnly exchange.application.*"

# Integration tests (Testcontainers — PG + Redis)
sbt "testOnly exchange.infrastructure.*"
sbt "testOnly exchange.api.*"

# Full suite
sbt test

# Docker smoke test
docker compose up -d
curl -s http://localhost:8080/health | jq .
docker compose down
```

### Manual Verification
- After Milestone 10: Execute the full trading flow via curl/httpie
- After Milestone 15: `docker compose up` from a clean checkout — verify zero-config startup
- After Milestone 16: Review README for completeness, run `sbt test` from clean state

---

## Milestone Dependency Graph

```mermaid
graph TD
    M1[M1: Skeleton & Docker] --> M2[M2: Users Domain]
    M2 --> M3[M3: JWT Auth & API]
    M3 --> M4[M4: Accounts & Balances]
    M4 --> M5[M5: Deposits & Withdrawals]
    M5 --> M6[M6: Trading Pairs & Orders]
    M6 --> M7[M7: Order Book]
    M7 --> M8[M8: Matching Engine]
    M8 --> M9[M9: Trading Service]
    M9 --> M10[M10: Trade & Order API]
    M10 --> M11[M11: Market Data & Redis]
    M11 --> M12[M12: Ticker Refresh Job]
    M10 --> M13[M13: Audit Log]
    M13 --> M14[M14: Admin Operations]
    M10 --> M15[M15: Docker App]
    M14 --> M16[M16: Integration Tests & Docs]
    M15 --> M16
    M12 --> M16
```

---

## Summary Table

| # | Milestone | New Tables | Key Deliverable |
|---|---|---|---|
| 1 | Skeleton & Docker | — | Bootable app + health check |
| 2 | Users Domain | `users` | User entity, registration, password hashing |
| 3 | JWT Auth & API | — | Auth middleware, login/register endpoints |
| 4 | Accounts & Balances | `accounts` | Balance domain logic, hold/release |
| 5 | Deposits & Withdrawals | `transfers` | Simulated funding, transactional balance ops |
| 6 | Trading Pairs & Orders | `trading_pairs`, `orders` | Order model, validation, placement |
| 7 | Order Book | — | Pure immutable order book data structure |
| 8 | Matching Engine | — | Pure matching function (heart of the project) |
| 9 | Trading Service | `trades` | End-to-end order → match → settle flow |
| 10 | Trade & Order API | — | Full trading REST API |
| 11 | Market Data & Redis | — (Redis) | Tickers, 24h stats, caching |
| 12 | Ticker Refresh | — | Background scheduler fiber |
| 13 | Audit Log | `audit_events` | Append-only event trail |
| 14 | Admin Operations | — | Admin dashboard API |
| 15 | Docker App | — | Containerized deployment |
| 16 | Integration & Docs | — | E2E tests, README, portfolio-ready |

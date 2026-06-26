# Crypto Exchange Implementation Plan

## Experiment metadata

Model: qwen2.5-coder:7b-instruct
Provider/runtime: Hugging Face
Quantization: BF16  
Task: crypto exchange implementation plan  
Date: 2026-06-09  
Prompt version: v1  
Result type: raw model output

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
```text
Implementation Plan for Educational Crypto Exchange Backend:

Goal: Setup Development Environment
Files to create:
build.sbt
project/plugins.sbt (to add necessary plugins)
Main Classes:
App.scala: Application entry point.
Database Tables: None initially
Tests to Write:
Unit test for application startup.
Expected Result: A runnable development environment with basic dependencies set up.
Goal: Define Core Data Models
Files to create:
domain/models.scala
Main Classes/Interfaces/Objects:
User case class with id, username, and email.
Account case class with user_id and balance fields.
TradeOrder case class representing buy/sell actions.
TradingPair case class with base currency and quote currency.
MatchingEngine interface or trait for handling order matches.
MarketData case class for latest prices across trading pairs.
Expected Result: Core models of the system are defined using Scala case classes.
Goal: Implement Database Persistence
Files to create:
repositories/user_repositories.scala
repositories/account_balance_repositories.scala
repositories/trade_order_repositories.scala
Main Functions/Subfunctions:
getUserById(id: UserId): Option[User]
saveTradeOrder(order: TradeOrder): IO[Void]
updateAccountBalance(userId: UserId, amount: BigDecimal): IO[Void]
Database Tables:
users
account_balances
trade_orders
Expected Result: CRUD operations on the defined databases can be performed.
Goal: Design HTTP API
Files to create:
web/routes.scala
web/http_controllers/user_controller.scala
web/http_controllers/account_and_balance_controller.scala
web/json_formats.scala: For JSON serialization/deserialization.
Main Classes/Functions/Subfunctions:
Endpoints to handle registration, login, and account management.
Endpoint for placing trades.
API Documentation: Draft an API documentation using Swagger or similar tool.
Expected Result: APIs are functional for primary user activities such as login/register and account management.
Goal: Simulate Deposits & Withdrawals
Files to create:
services/funds_service.scala
Main Functions/Subfunctions:
depositFunds(user_id:UserId, source_account_id:AccountId, amount:BigDecimal): IO[Unit]
withdrawFunds(user_id:UserId, target_account_id:AccountId, amount:BigDecimal): IO[Unit]
Expected Result: Methods allow simulating fund transfers between users' simulated accounts.
Goal: Initialize Order Book for Trading Pairs
Files to create:
order_books/orderbook_manager.scala
Main Functions/Subfunctions:
addTradingPair(pair:TradingPair): IO[Unit]
getOrdersForPair(pair:TradingPair): Stream[IO, TradeOrder]
Expected Result: An order book initialized and capable of storing multiple open trade orders per pair.
Goal: Admin Operations Integration
Files to create:
admin/admin_operations_controller.scala
Main Functions/Subfunctions:
endpoints allowing admins to monitor system activity and adjust settings.
Expected Result: Functional Admin-only routes available on the API.
Goal: Matching Engine Implementation
Files to create:
matchingengine/matching_logic.scala
matchingengine/match_result.scala
Main Function/Subfunction:
matchOrders(): Function to attempt matching opposing trades in the order book.
Expected Result: A rudimentary mechanism exists for orders to auto-execute based on current prices.
Goal: Audit Logging Mechanism
Files to create:
audit/audit_logger.scala
Main Function/Subfunction:
logAction(action:ActionType): IO[Unit]
Expected Result: Logs all significant actions performed within the system.
Goal: Integration Testing for Core Components
Files to create:
core_integration_tests.scala
Main Subfunctions:
Test end-to-end interactions between the repositories, services, and HTTP controllers.
Expected Result: All core components interact reliably.
Goal: Security Layer – Authentication & Authorization
Files to create:
security/authentication_layer.scala
security/authorization_layer.scala
Main Functions/Subfunctions:
authenticateUser(credentials:Credentials): IO[Boolean]
checkAuthorization(request:Request, user:User): IO[Boolean]
Expected Result: Basic authentication and authorization logic exists.
Goal: Test Driven Development for Key Features
Files to create:
Detailed tests for Deposit/Withdraw, Placing/Buying/Selling Orders, etc. under web/http_controllers/.
Main SubFunctions/Classes:
Specific tests demonstrating the correct functionality of each feature.
Expected Result: High confidence in the core functionalities of your backend.
Goal: Refactor Codebase into Scalable Structure
Files to create/update: Organize existing packages and move them according to their functionality.
Directory Structure Changes:
Move domain models to a dedicated package.
Isolate HTTP related files into a separate package.
Encapsulate infrastructure implementations into one.
Expected Result: Clean and organized codebase ready for further scalability.
Goal: Add Rate Limiting Middleware to Prevent Abuse
Files to create/update: Integrate rate limiting library.
Main Function/Subfunction:
Middleware layer for rate controlling API calls based on user IP addresses.
Expected Result: A layer ensures that no single user can overload the system.
Goal: Develop Unit/Property Based Tests for Infrastructure
Files to create/update: Infrastructure repository tests, simulation service tests.
Main Subfunctions/Classes:
Detailed unit tests for database read/write methods along with property-based checks (using libraries like GenScala).
Expected Result: Robust infrastructure layer that can operate in isolation for testing purposes.
Goal: Monitor System Health With Prometheus Metrics
Files to create:
metrics/metrics_collector.scala
Configuration File Changes:
Enable Prometheus monitoring metrics in your infrastructure setup (like PgBouncer stats for PostgreSQL).
Expected Result: Real-time system health information accessible via Prometheus metrics.
Goal: Set Up Continuous Integration / Continuous Deployment
Configuration File Changes: Update your CI/CD workflow file (.yml) to automatically compile, run tests, and deploy upon passing changes.
GitHub Actions Configuration: Example setup included within project for GitHub CI/CD.
Expected Result: Fully automated deployment process for any new commits which pass integration tests.

Goal: Final Testing And Verification Before Documentation
Final Integration and UI Testing: Conduct comprehensive testing ensuring everything integrates correctly.
Code Review and Quality Assurance: Perform a manual code review and refactorings as applicable to final quality assurance.
Documentation: Prepare complete documentation including API docs (Swagger), system architecture diagrams, and usage guides for potential future developers working on this project.
Expected Result: Thoroughly tested backend system with clear and concise documentation, ready to demonstrate skills and knowledge.
```
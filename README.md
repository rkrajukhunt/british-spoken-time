# British Spoken Time API

> A production-ready REST API that converts digital time (HH:mm) to British spoken form.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Coverage](https://img.shields.io/badge/Coverage-80%25+-success.svg)]()

## 📖 Table of Contents

- [Overview](#overview)
- [Quick Start](#quick-start)
- [API Usage](#api-usage)
- [Conversion Examples](#conversion-examples)
- [Architecture & Design](#architecture--design)
- [Testing](#testing)
- [Development](#development)
- [License](#license)

---

## 🎯 Overview

Converts digital time to British English spoken form:

| Input | Output |
|-------|--------|
| `12:00` | noon |
| `00:00` | midnight |
| `7:30` | half past seven |
| `9:45` | quarter to ten |
| `6:32` | six thirty two |

### Key Features

- ✅ RESTful API with JSON responses
- ✅ Comprehensive input validation
- ✅ Interactive Swagger UI documentation
- ✅ Clean architecture with SOLID principles
- ✅ CI/CD with GitHub Actions

---

## 🚀 Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.8+ (or use included `./mvnw`)

### Installation & Running

```bash
# Clone repository
git clone https://github.com/rkrajukhunt/british-spoken-time.git
cd british-spoken-time

# Build project
./mvnw clean install

# Run application
./mvnw spring-boot:run
```

**API will be available at:** `http://localhost:8080`

### Swagger Documentation

Interactive API documentation:
```
http://localhost:8080/swagger-ui.html
```

---

## 📡 API Usage

### Endpoint: Convert Time

#### POST Request

```bash
curl -X POST http://localhost:8080/api/v1/time/convert \
  -H "Content-Type: application/json" \
  -d '{"time":"7:30"}'
```

**Response:**
```json
{
  "time": "7:30",
  "spokenForm": "half past seven"
}
```

### Input Format

- **Format:** HH:mm (24-hour)
- **Hours:** 0-23
- **Minutes:** 0-59
- **Examples:** `00:00`, `12:30`, `23:59`

### Error Responses

```json
{
  "timestamp": "2025-11-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Time must be in HH:mm format (00:00 to 23:59)",
  "path": "/api/v1/time/convert"
}
```

---

## 🕐 Conversion Examples

### Common Times

| Input | Output |
|-------|--------|
| `00:00` | midnight |
| `12:00` | noon |
| `1:00` | one o'clock |
| `7:30` | half past seven |
| `9:45` | quarter to ten |

### All Conversion Patterns

| Input   | Output                 | Rule |
|---------|------------------------|------|
| `00:00` | midnight               | Special case |
| `12:00` | noon                   | Special case |
| `1:00`  | one o'clock            | On the hour (X:00) |
| `2:05`  | five past two          | Past (1-30 minutes) |
| `4:15`  | quarter past four      | Quarter past (X:15) |
| `5:20`  | twenty past five       | Past (1-30 minutes) |
| `6:32`  | six thirty two         | Special format (32-34, 37-39) |
| `7:30`  | half past seven        | Half past (X:30) |
| `7:35`  | twenty five to eight   | To (31-59 minutes) |
| `8:40`  | twenty to nine         | To (31-59 minutes) |
| `9:45`  | quarter to ten         | Quarter to (X:45) |
| `11:55` | five to twelve         | To (31-59 minutes) |

---

## 🏗️ Architecture & Design

### Design Patterns

#### 1. **Strategy Pattern**
Each time formatting rule in its own strategy class:
- `MidnightStrategy`, `NoonStrategy`, `OClockStrategy`
- `QuarterPastStrategy`, `HalfPastStrategy`, `QuarterToStrategy`
- `MinutesPastStrategy`, `SpecialMinutesStrategy`, `MinutesToStrategy`

**Benefits:** Easy to add new rules without modifying existing code (Open/Closed Principle)

#### 2. **Factory Pattern**
`TimeFormatStrategyFactory` selects the appropriate strategy dynamically with priority-based selection.

#### 3. **Dependency Injection**
Constructor-based DI via Spring for loose coupling and testability.

### SOLID Principles

- **Single Responsibility:** Each class has one clear purpose
- **Open/Closed:** Extensible through interfaces
- **Liskov Substitution:** Strategy implementations are interchangeable
- **Interface Segregation:** Focused, minimal interfaces
- **Dependency Inversion:** Depend on abstractions

### Project Structure

```
src/main/java/com/britishspokentime/
├── config/              # Spring configuration
├── constants/           # TimeConstants (no magic numbers)
├── controller/          # REST endpoints (no business logic)
├── domain/              # Domain entities (Time model)
├── dto/                 # Request/Response objects
├── exception/           # Global error handling
└── service/
    ├── TimeService      # Business logic orchestration
    ├── factory/         # Strategy factory
    ├── strategy/        # 9 strategy implementations
    └── util/            # Number-to-word converter
```

### Layered Architecture

The application follows a clean layered architecture:

```
Controller Layer → Service Layer → Converter Layer → Strategy Layer
     (HTTP)       (Business Logic)  (Domain Logic)   (Formatting)
```

- **Controller:** Handles HTTP requests/responses only
- **Service:** Contains all business logic and orchestration
- **Converter:** Core domain conversion logic
- **Strategy:** Individual formatting implementations

---

## 🧪 Testing

### Test Coverage

- **295 Total Tests** - All passing ✅
- **Unit Tests:** 110+ tests for business logic
- **Integration Tests:** 27 tests for API endpoints
- **Service Tests:** 20 tests for service layer
- **Factory Tests:** 11 tests for strategy selection
- **Coverage:** 80%+ with Jacoco

### Running Tests

```bash
# Run all tests
./mvnw test

# Generate coverage report
./mvnw jacoco:report

# View report
open target/site/jacoco/index.html
```

---

## 🛠️ Development

### Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Programming language (LTS) |
| Spring Boot | 3.5.7 | Application framework |
| SpringDoc OpenAPI | 2.8.5 | API documentation |
| Lombok | Latest | Reduce boilerplate |
| JUnit 5 | Latest | Testing framework |
| Jacoco | 0.8.12 | Code coverage |
| Maven | 3.8+ | Build tool |

### Code Quality

```bash
# Run checkstyle
./mvnw checkstyle:check

# Run all quality checks
./mvnw clean verify
```

### CI/CD

GitHub Actions workflow includes:
- ✅ Automated testing
- ✅ Code coverage reporting
- ✅ Build verification
- ✅ Checkstyle validation

---

## 📄 License

This project is licensed under the MIT License.

## 👤 Author

**Raju Khunt**
- GitHub: [@rkrajukhunt](https://github.com/rkrajukhunt)
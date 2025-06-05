# 📸 Media-App

[![Build & Test](https://github.com/zRaincoat/media-app/actions/workflows/ci.yml/badge.svg)](https://github.com/zRaincoat/media-app/actions)
![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

A **clean, test-driven Spring Boot backend** for cataloguing and sharing media (photos, videos, audio—extend as you like).  
> ✅ **Thoroughly tested end-to-end, tuned to eliminate N + 1 queries and other performance bottlenecks, and ships with a ready-to-use Postman collection for instant API exploration.**

---

## 🧐 Problem it solves

| Pain point | How Media-App helps |
|------------|--------------------|
| ❌ Every side-project starts with the same boilerplate—security, persistence, error handling. | ✅ Pre-configured Spring Security and full CRUD scaffolding. |
| ❌ Hidden N + 1 queries tank performance. | ✅ JPA fetch plans & query hints remove N + 1 issues; SQL logs covered by integration tests. |
| ❌ Manual deployment and flaky builds waste time. | ✅ **CI/CD with GitHub Actions**: every push runs tests & builds a verified artifact. |
| ❌ Code without tests breaks after each push | ✅ **Code covered with unit tests that run before each PR** |

---

## ⚙️ Tech stack

| Layer | Library / Tool | Why |
|-------|----------------|-----|
| Runtime | **Spring Boot 3** | Rapid setup & production-ready defaults |
| Persistence | **PostgreSQL** + **Spring Data JPA** | Powerful SQL, declarative repositories |
| Security | **Spring Security** (JWT) | Stateless auth with token support out of the box |
| Build | **Maven** | Convention > config, huge plugin ecosystem |
| Logging | **SLF4J / Logback** | Flexible binding, structured logs |
| Dev & Ops | **GitHub Actions CI** | Lint → test → package on every push |
| Testing | **JUnit5 / Mockito** | Every piece of logic is covered with unit tests |

---

## 🚀 Getting started

### Prerequisites

* **Java 21 JDK**
* **Maven 3.9+**
* **PostgreSQL 15** (local or Docker)
* _Optional_ : **Docker Compose** for one-command startup

### Run the Project 💻

**1) Clone the repository**

```bash
git clone https://github.com/zRaincoat/media-app.git
cd media-app
```

**2) Configure PostgreSQL locally**

- Add application properties  
- Create `src/main/resources/application.properties` (or `application.yml`) with your DB creds:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/media_app
spring.datasource.username=media
spring.datasource.password=secret
```

**3) Import the Postman collection**

File: `media_app.postman_collection.json` (in the repo root).

```text
Open Postman → Import → select the file
```

**4) Run the application with Maven**

```bash
mvn spring-boot:run
# or build & run the jar
mvn -Pprod clean package
java -jar target/media-app-*.jar
```

The API starts at `http://localhost:8080`.

**5) Register, login, enjoy — all in Postman**

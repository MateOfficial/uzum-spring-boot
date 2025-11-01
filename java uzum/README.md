# Uzum Java Internship Test

This is my simple REST API for Uzum internship test (task about Person and Car).

## Stack
- Java 17
- Spring Boot 3.2
- PostgreSQL (run in Docker)
- Gradle (Kotlin DSL)
- Lombok
- JPA / Hibernate
- Jacoco / JUnit 5 (for tests)

## How to run

1. **(Option 1) With Docker Compose:**

First, you need Docker and Docker Compose installed.

```
docker-compose up --build
```

It will run PostgreSQL and the Spring Boot app. App is on [http://localhost:8080](http://localhost:8080)

2. **(Option 2) Local Start:**

- Start PostgreSQL (user: postgres, password: postgres, db: uzumdb)
- Build and run app:

```
./gradlew.bat clean build
java -jar build/libs/*.jar
```

## API Endpoints

- `POST /person` — add new person
- `POST /car` — add new car
- `GET /personwithcars?personid=123` — get person with car list
- `GET /statistics` — show statistics
- `GET /clear` — delete all persons and cars

## Request/Response example:

**Add Person:**
```
POST /person
{
  "id": 1,
  "name": "John",
  "birthdate": "01.01.2000"
}
```

**Add Car:**
```
POST /car
{
  "id": 101,
  "model": "BMW-X5",
  "horsepower": 200,
  "ownerId": 1
}
```

**Person With Cars:**
```
GET /personwithcars?personid=1
// response: see PersonWithCarsResponse.java
```

**Statistics:**
```
GET /statistics
// Shows: personcount, carcount, uniquevendorcount
```

**Clear:**
```
GET /clear
```

## Tests & Coverage

All main logic is covered by unit tests (see `/src/test/java/...`).
To get Jacoco coverage report:
```
./gradlew.bat test jacocoTestReport
```
See report in:
```
build/reports/jacoco/test/html/index.html
```

## What to screenshot for test delivery:
- Postman (good API calls)
- Database with data
- Jacoco coverage report
- Running project (Tomcat logs)

---

Simple comments and code to show my logic. If you have questions — please write!

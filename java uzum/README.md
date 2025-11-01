# Uzum Java Internship Test

Задания стажировки Uzum.

Коротко о стеке
- Java 17+
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- Validation (Jakarta)
- Gradle (Kotlin DSL)
- JUnit5, JaCoCo, Testcontainers (опционально для интеграционных тестов)

Короткие инструкции

1) Запуск через Docker Compose (Postgres + приложение)

  Требуется установленный Docker и Docker Compose.


docker-compose up --build

  Приложение будет доступно по: http://localhost:8080

2) Локальный запуск

 
  Технологии
  ---------
  - Java 17+
  - Spring Boot 3.x
  - Spring Data JPA (Hibernate)
  - H2 (встроенно для локального запуска)
  - Gradle (Kotlin DSL)
  - JUnit 5, JaCoCo

  API (основные эндпоинты)
  --------------------------------
  - POST /person — создать Person (JSON: id, name, birthdate)
  - POST /car — создать Car (JSON: id, model, horsepower, ownerId)
  - GET /personwithcars?personid={id} — получить Person с автомобилями
  - GET /statistics — получить статистику
  - GET /clear — очистить данные (только для локальной проверки)

  Файлы в репозитории
  --------------------
  - `src/main/java` — исходники сервиса
  - `src/test/java` — юнит‑тесты
  - `build.gradle.kts` — настройки сборки
  - `postman_collection_uzum.json` — готовая коллекция для Postman (импорт)
  - `verify.ps1` — скрипт быстрой локальной проверки (опционально)

  Как запустить (коротко)
  ----------------------
  Самые простые команды для локальной проверки (Windows / PowerShell) либо использовать intellijIdea:

  ```powershell
  .\gradlew.bat clean test jacocoTestReport
  .\gradlew.bat bootJar
  java -jar build\libs\uzum-java-student-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:h2:mem:testdb
  ```

  После старта UI доступен по адресу: http://localhost:8080/

  Тестирование и покрытие
  -----------------------
  - Юнит‑тесты запускаются командой `./gradlew test` (в репозитории используется Gradle wrapper).
  - JaCoCo HTML‑отчёт генерируется вместе с `jacocoTestReport` и находится в `build/reports/jacoco/test/html/index.html`.

  Postman
  -------
  В корне проекта есть `postman_collection_uzum.json` — импортируйте его в Postman для быстрого набора запросов (Create Person, Create Car, Get PersonWithCars, Get Statistics).

  
  Docker / Docker Compose
  -----------------------
  В репозитории присутствует `Dockerfile` и `docker-compose.yml` для запуска стека с Postgres. Команда:


  docker-compose up --build


  



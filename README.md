# Worch

---


**Worch** — мобильное приложение и веб-сервис, который помогает людям принимать решения путём голосования. Пользователь создаёт choise — выбор между двумя и более вариантами — и отправляет его друзьям, подписчикам каналов или экспертам.

## Технологии

- Spring Boot 3 (Java 21)
- PostgreSQL
- Keycloak (аутентификация и авторизация)
- REST API, Swagger UI
- Flyway для миграций
- Docker и Docker Compose

---

## Требования

- Java 21
- Maven 3.8+
- Docker и Docker Compose

---

## Подготовка к запуску

Перед запуском убедитесь, что создана внешняя сеть для объединения сервисов (если ещё не создана):

```bash
docker network create worch-internal
```
Затем необходимо собрать JAR-файл приложения:
```
mvn clean package -DskipTests
```
Запуск системы

Если требуется локальная база данных, запустите её:

```
docker compose -f docker-compose-local.yml up -d
```

Для запуска Keycloak и Spring Boot приложения:

```
docker compose -f docker-compose.yml up --build -d
```

Проверка доступности сервисов

- Keycloak: http://localhost:9090
- REST API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html

Запуск тестов

```
mvn test
```

Хранение данных

Данные Keycloak сохраняются в Docker-томе keycloak_data

Данные PostgreSQL — в томе db_data

Конфигурация через .env

Все параметры подключения к базе данных выносятся в файл .env в корне проекта:

```
POSTGRES_HOST=worch_db
POSTGRES_PORT=5432
POSTGRES_DB=worch_db
POSTGRES_USER=test
POSTGRES_PASSWORD=test
```

Структура Docker Compose
Файл	Назначение
docker-compose.yml	Основной файл: приложение и Keycloak
docker-compose-local.yml	Отдельный файл для локальной базы данных
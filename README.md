# Rodojacto

Projeto full-stack com backend em Spring Boot (Kotlin) e frontend em Angular.

## Tecnologias

### Backend
- **Kotlin** 2.2.21
- **Spring Boot** 4.0.6 (Web MVC, Data JPA, Security, Validation, Actuator)
- **Java** 21
- **MySQL** (driver `mysql-connector-j`)
- **Flyway** (migrations)
- **JWT** (`jjwt` 0.12.6) para autenticação
- **springdoc-openapi** (Swagger UI)
- **Gradle** (Kotlin DSL)

### Frontend
- **Angular** 21
- **TailwindCSS** 4


## Pré-requisitos

- JDK 21
- Node.js (compatível com Angular 21) e npm 11+
- MySQL 8+ em execução

## Backend

### 1. Banco de dados

Crie o banco e o usuário usados em [backend/src/main/resources/application.properties](backend/src/main/resources/application.properties):

```sql
CREATE DATABASE mysql-rodojacto;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON mysql-rodojacto.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

Ajuste `spring.datasource.*` se quiser outras credenciais.

### 2. Variáveis de ambiente (opcional)

- `JWT_SECRET` — chave de assinatura do JWT (há um default apenas para desenvolvimento).

### 3. Rodar

```bash
cd backend
./gradlew bootRun
```

A API sobe em `http://localhost:8080`.

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Actuator: `http://localhost:8080/actuator`

As migrations Flyway rodam automaticamente no boot.

## Frontend

### 1. Instalar dependências

```bash
cd frontend
npm install
```

### 2. Rodar em desenvolvimento

```bash
npm start
```

App disponível em `http://localhost:4200`.


## Estrutura

```
rodojacto/
├── backend/   # Spring Boot + Kotlin
└── frontend/  # Angular + NgRx
```

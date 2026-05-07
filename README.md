# Restaurante Project 🍽️

API REST desenvolvida com Java e Spring Boot para gerenciamento de restaurante, incluindo autenticação JWT, controle de permissões, gerenciamento de pratos e pedidos.

---

## 🚀 Tecnologias utilizadas

- Java 25
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok
- Jakarta Validation

---

## 🔐 Funcionalidades

### Autenticação
- Registro de usuários
- Login com JWT
- Autenticação Stateless
- Controle de acesso por Roles

### Pratos
- Criar pratos
- Atualizar pratos
- Deletar pratos
- Buscar pratos
- Listar pratos

### Pedidos
- Criar pedidos
- Adicionar itens ao pedido
- Atualizar status do pedido
- Finalizar pedido
- Cancelar pedido

---

## 🛡️ Segurança

O projeto utiliza:

- Spring Security
- JWT Token Authentication
- BCrypt Password Encoder
- Filtro personalizado de autenticação
- Controle de acesso com Roles (`ROLE_ADMIN`, `ROLE_CLIENTE`)

---

## 📂 Estrutura do projeto

```text
src/main/java/com/vitor/restaurante_project
│
├── config
├── controller
├── database
│   ├── model
│   └── repository
├── dto
├── enums
├── exception
├── service
```

---

## ⚙️ Configuração

### application.yml

```yml
server:
  port: 8082

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/restaurante_project?createDatabaseIfNotExist=true
    username: root
    password: sua_senha

  jpa:
    hibernate:
      ddl-auto: update
```

---

## ▶️ Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/VitorD1as/restaurante-project-spring.git
```

### 2. Entre na pasta

```bash
cd restaurante-project
```

### 3. Configure o MySQL

Crie um banco chamado:

```sql
CREATE DATABASE restaurante_project;
```

### 4. Execute o projeto

```bash
./mvnw spring-boot:run
```

---

## 🔑 Autenticação JWT

Após realizar login:

```http
POST /v1/auth/login
```

Será retornado um token JWT.

Utilize no header das requisições protegidas:

```http
Authorization: Bearer seu_token
```

---

## 📌 Endpoints principais

### Auth

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/v1/auth/register` | Registrar usuário |
| POST | `/v1/auth/login` | Login |

### Pratos

| Método | Endpoint |
|---|---|
| GET | `/v1/pratos` |
| GET | `/v1/pratos/{id}` |
| POST | `/v1/pratos` |
| PUT | `/v1/pratos/{id}` |
| DELETE | `/v1/pratos/{id}` |

### Pedidos

| Método | Endpoint |
|---|---|
| POST | `/v1/pedidos` |
| PUT | `/v1/pedidos/{pedidoId}/itens` |
| PUT | `/v1/pedidos/{pedidoId}/status` |
| PUT | `/v1/pedidos/finalizar/{pedidoId}` |
| PUT | `/v1/pedidos/cancelar/{pedidoId}` |

---

## 📖 Aprendizados

Projeto desenvolvido para aprofundar conhecimentos em:

- APIs REST
- Arquitetura em camadas
- Segurança com Spring Security
- Autenticação JWT
- Relacionamentos JPA
- Boas práticas no Spring Boot

---

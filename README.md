# Forum Hub - Challenge ONE Alura

Uma API REST moderna para gestão de tópicos de um fórum, desenvolvida com Java 17, Spring Boot 3 e as melhores práticas de desenvolvimento.

## 📋 Visão Geral

O **Forum Hub** é uma solução completa para criar, listar, atualizar e deletar tópicos de discussão em um fórum. A API segue os princípios REST e utiliza banco de dados relacional (MySQL) com Flyway para controle de migrações.

### ✨ Principais Características

- ✅ **Autenticação Segura**: JWT (JSON Web Token) com BCrypt
- ✅ **API REST Completa**: CRUD de tópicos totalmente funcional
- ✅ **Banco de Dados MySQL**: Persistência confiável com JPA
- ✅ **Migrações Automáticas**: Flyway para versionamento do BD
- ✅ **Validações**: Spring Validation em todos os DTOs
- ✅ **Variáveis de Ambiente**: Configurações sensíveis protegidas
- ✅ **Tratamento de Erros**: Exceções customizadas e globais
- ✅ **Paginação**: Suporte a Page e Pageable do Spring Data
- ✅ **Logging**: Rastreamento detalhado com SLF4J
- ✅ **Documentação**: Código comentado em PT-BR

---

## 🖥️ Requisitos do Sistema

### Mínimo

- **Java**: 17 ou superior (desenvolvido com Java 21)
- **Maven**: 3.8.0 ou superior
- **MySQL**: 8.0 ou superior
- **Git**: Para clonar o repositório

---

## 🏗️ Stack Tecnológico

| Camada             | Tecnologia                          |
| ------------------ | ----------------------------------- |
| **Framework**      | Spring Boot 3.2.0                   |
| **Segurança**      | Spring Security 6.1.1 + JWT (Auth0) |
| **Persistência**   | Spring Data JPA + Hibernate         |
| **Banco de Dados** | MySQL 8.0                           |
| **Migrações**      | Flyway 9.22.3                       |
| **Validações**     | Jakarta Validation                  |
| **Utilitários**    | Lombok 1.18.30                      |
| **Linguagem**      | Java 17+                            |
| **Build**          | Maven 3.8+                          |

---

## 📡 API Endpoints

### 🔓 Públicos (Sem Autenticação)

#### **POST /login** - Obter Token JWT

```bash
POST http://localhost:8080/login
Content-Type: application/json

{
  "login": "admin@forum.com",
  "senha": "123456"
}
```

---

### 🔒 Protegidos (Requerem Token JWT)

#### **POST /topicos** - Criar Novo Tópico

```bash
POST http://localhost:8080/topicos
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "titulo": "Como usar Spring Boot?",
  "mensagem": "Estou com dúvida em como configurar o Spring Boot",
  "autor": "João Silva",
  "curso": "Spring Boot Iniciante"
}
```

#### **GET /topicos** - Listar Tópicos (Paginado)

```bash
GET http://localhost:8080/topicos?page=0&size=10&sort=dataCriacao,desc
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### **GET /topicos/{id}** - Obter Detalhes

```bash
GET http://localhost:8080/topicos/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### **PUT /topicos/{id}** - Atualizar Tópico

```bash
PUT http://localhost:8080/topicos/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "titulo": "Como usar Spring Boot? [Resolvido]",
  "status": "SOLUCIONADO"
}
```

#### **DELETE /topicos/{id}** - Deletar Tópico

```bash
DELETE http://localhost:8080/topicos/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

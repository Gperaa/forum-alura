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

## ⚙️ Instalação e Configuração

### 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/forum-hub.git
cd forum-hub
```

### 2️⃣ Criar Banco de Dados MySQL

```bash
# Abrir MySQL
mysql -u root -p

# Executar SQL
CREATE DATABASE forum;
EXIT;
```

### 3️⃣ Configurar Variáveis de Ambiente

```bash
# Copiar arquivo de exemplo
cp .env.example .env

# Editar arquivo com seus valores
# nano .env (ou use seu editor favorito)
```

**Valores essenciais a configurar em `.env`:**

```properties
DATABASE_USERNAME=root
DATABASE_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta_forte_minimo_256_bits
```

📚 **Veja [ENVIRONMENT.md](ENVIRONMENT.md) para documentação completa** das variáveis de ambiente.

### 4️⃣ Instalar Dependências e Executar

```bash
# Limpar build anterior
mvn clean

# Baixar dependências e compilar
mvn install

# Executar a aplicação
mvn spring-boot:run
```

### ✅ Aplicação Rodando

```
A aplicação estará disponível em: http://localhost:8080/topicos
```

---

## 🔐 Autenticação e Segurança

### Credenciais Padrão (Apenas Desenvolvimento)

```
Login: admin@forum.com
Senha: 123456
```

⚠️ **Alterar em produção!**

### Fluxo de Autenticação

1. **Enviar credenciais**: POST `/login`
2. **Receber token**: Resposta com `{ "token": "...", "tipo": "Bearer" }`
3. **Usar token**: Enviar no header `Authorization: Bearer <token>` em outros endpoints

### Obter Token JWT

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "login": "admin@forum.com",
    "senha": "123456"
  }'
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer"
}
```

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

---

## 📁 Estrutura do Projeto

```
forum/
├── src/
│   ├── main/
│   │   ├── java/br/com/alura/forum/
│   │   │   ├── config/                 # Configurações
│   │   │   │   └── SecurityConfigurations.java
│   │   │   ├── controller/             # Controllers REST
│   │   │   │   ├── AutenticacaoController.java
│   │   │   │   └── TopicoController.java
│   │   │   ├── domain/                 # Entidades JPA
│   │   │   │   ├── topico/
│   │   │   │   └── usuario/
│   │   │   ├── dto/                    # Data Transfer Objects
│   │   │   │   ├── autenticacao/
│   │   │   │   └── topico/
│   │   │   ├── exception/              # Exceções customizadas
│   │   │   ├── handler/                # Global Exception Handler
│   │   │   ├── repository/             # Data Access
│   │   │   ├── security/               # Segurança JWT
│   │   │   ├── service/                # Lógica de Negócio
│   │   │   └── ForumHubApplication.java
│   │   └── resources/
│   │       ├── application.properties      # Configuração base
│   │       ├── application-prod.properties # Configuração produção
│   │       └── db/migration/               # Migrações SQL
│   └── test/
├── .env.example                # Exemplo de variáveis de ambiente
├── .env                         # Variáveis de ambiente (NÃO commitar)
├── .gitignore
├── ENVIRONMENT.md               # Documentação de variáveis
├── pom.xml
└── README.md
```

---

## 🧪 Testando a API

### Com cURL

```bash
# 1. Obter token
TOKEN=$(curl -s -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin@forum.com","senha":"123456"}' | \
  jq -r '.token')

# 2. Usar token
curl -X GET http://localhost:8080/topicos \
  -H "Authorization: Bearer $TOKEN"
```

### Com Postman

1. Importar collection de exemplo
2. Configurar variável `token` na environment
3. Usar scripts para atualizar token automaticamente
4. Testar endpoints protegidos

---

## 🔐 Segurança em Produção

### ✅ Checklist Pré-Deploy

- [ ] Arquivo `.env` NÃO está em git
- [ ] `JWT_SECRET` foi alterada (mínimo 256 bits)
- [ ] `DATABASE_PASSWORD` é uma senha forte
- [ ] `JPA_DDL_AUTO` está em `validate`
- [ ] Logs estão configurados para WARN/ERROR
- [ ] HTTPS está habilitado

### Executar em Produção

```bash
# Com variáveis de ambiente do SO
export DATABASE_URL="jdbc:mysql://..."
export DATABASE_USERNAME="user"
export DATABASE_PASSWORD="senha"
export JWT_SECRET="chave-forte-256bits"

java -jar forum-hub.jar --spring.profiles.active=prod
```

Ou com arquivo `.env`:

```bash
# Se usar Docker
docker-compose --env-file .env up

# Se usar Java direto
java -jar forum-hub.jar
```

---

## 📚 Documentação Detalhada

- [ENVIRONMENT.md](ENVIRONMENT.md) - Configuração de variáveis de ambiente
- [Documentação de Endpoints](docs/ENDPOINTS.md) - Detalhes de cada endpoint
- [Guia de Segurança](docs/SECURITY.md) - Práticas de segurança

---

## 🚧 Melhorias Futuras

- [ ] Swagger/OpenAPI para documentação automática
- [ ] Refresh tokens para melhor UX
- [ ] Rate limiting para proteção contra brute-force
- [ ] CORS configurável
- [ ] Cache com Redis
- [ ] Testes automatizados (JUnit 5, MockMvc)
- [ ] CI/CD com GitHub Actions
- [ ] Docker e docker-compose
- [ ] Entidade de Respostas para tópicos
- [ ] Implementar roles e permissões

---

## 🆘 Troubleshooting

### Erro: "Access denied for user 'root'@'localhost'"

Verificar credenciais no arquivo `.env`:

```bash
mysql -u root -p  # Digitar senha do .env
```

### Erro: "Cannot load driver class: com.mysql.cj.jdbc.Driver"

MySQL não está rodando:

```bash
# Iniciar MySQL
mysql.server start  # macOS
sudo service mysql start  # Linux
```

### Erro ao fazer login

Verificar se a migration V2 foi executada:

```sql
SELECT * FROM usuarios;
```

---

## 📝 Licença

Este projeto foi desenvolvido como Challenge da Alura. Use livremente para fins educacionais.

---

**Desenvolvido com ❤️ para a Alura**

_Última atualização: Janeiro 2026_

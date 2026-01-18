# Forum Hub - Challenge ONE Alura

Uma API REST moderna para gestão de tópicos de um fórum, desenvolvida com Java 17, Spring Boot 3 e as melhores práticas de desenvolvimento.

## 📋 Visão Geral

O **Forum Hub** é uma solução completa para criar, listar, atualizar e deletar tópicos de discussão em um fórum. A API segue os princípios REST e utiliza banco de dados relacional (H2 ou MySQL) com Flyway para controle de migrações.

## 🎯 Objetivo da Sprint 1

Implementar o CRUD completo para a entidade `Topico`, com validações robustas e tratamento adequado de erros.

## 🛠️ Stack Tecnológico

- **Linguagem**: Java 17
- **Framework**: Spring Boot 3.2.0
- **Gerenciamento de Dependências**: Maven
- **Banco de Dados**: H2 (desenvolvimento) / MySQL (produção)
- **ORM**: Spring Data JPA / Hibernate
- **Validação**: Spring Validation
- **Migrações**: Flyway
- **Redução de Boilerplate**: Lombok
- **Logging**: SLF4J (incluído no Spring Boot)

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/alura/forum/
│   │   ├── controller/
│   │   │   └── TopicoController.java
│   │   ├── domain/
│   │   │   └── topico/
│   │   │       ├── Topico.java
│   │   │       └── StatusTopico.java
│   │   ├── dto/
│   │   │   ├── topico/
│   │   │   │   ├── CadastroTopicoDto.java
│   │   │   │   ├── AtualizacaoTopicoDto.java
│   │   │   │   └── DetalhesTopicoDto.java
│   │   │   └── exception/
│   │   │       ├── ErroResponseDto.java
│   │   │       └── CampoErroDto.java
│   │   ├── exception/
│   │   │   ├── TopicoNaoEncontradoException.java
│   │   │   └── TopicoJaExisteException.java
│   │   ├── handler/
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── repository/
│   │   │   └── TopicoRepository.java
│   │   ├── service/
│   │   │   └── TopicoService.java
│   │   └── ForumHubApplication.java
│   └── resources/
│       ├── application.properties
│       └── db/migration/
│           └── V1__criar_tabela_topicos.sql
└── test/
```

## 🚀 Executando a Aplicação

### Pré-requisitos

- JDK 17 ou superior instalado
- Maven 3.8.0 ou superior
- (Opcional) MySQL 8.0+ para usar em produção

### Passos para Execução

1. **Clone ou abra o projeto** no diretório `c:\Users\Gabriel\dev\forum`

2. **Compile o projeto**:

   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**:

   ```bash
   mvn spring-boot:run
   ```

   Ou execute a classe `ForumHubApplication.java` diretamente na sua IDE.

4. **Verifique se está rodando**:
   - Acesse: `http://localhost:8080/api/topicos`
   - Ou acesse o console H2: `http://localhost:8080/api/h2-console`
   - URL: `jdbc:h2:mem:forumdb`
   - Usuário: `sa`
   - Senha: (deixe em branco)

## 📝 Endpoints da API

### 1. Criar Novo Tópico

**POST** `/api/topicos`

**Request Body**:

```json
{
  "titulo": "Como usar Spring Boot?",
  "mensagem": "Estou com dúvida em como configurar o Spring Boot para a primeira vez. Alguém pode me ajudar?",
  "autor": "João Silva",
  "curso": "Spring Boot Iniciante"
}
```

**Response** (201 Created):

```json
{
  "id": 1,
  "titulo": "Como usar Spring Boot?",
  "mensagem": "Estou com dúvida em como configurar o Spring Boot para a primeira vez. Alguém pode me ajudar?",
  "dataCriacao": "2026-01-18T10:30:00",
  "status": "NAO_RESPONDIDO",
  "autor": "João Silva",
  "curso": "Spring Boot Iniciante"
}
```

### 2. Listar Todos os Tópicos

**GET** `/api/topicos?page=0&size=20&sort=dataCriacao,desc`

**Response** (200 OK):

```json
{
  "content": [
    {
      "id": 1,
      "titulo": "Como usar Spring Boot?",
      "mensagem": "Estou com dúvida...",
      "dataCriacao": "2026-01-18T10:30:00",
      "status": "NAO_RESPONDIDO",
      "autor": "João Silva",
      "curso": "Spring Boot Iniciante"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": { "empty": false, "sorted": true, "unsorted": false }
  },
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 20,
  "first": true,
  "last": true,
  "empty": false
}
```

### 3. Obter Detalhes de um Tópico

**GET** `/api/topicos/{id}`

**Response** (200 OK):

```json
{
  "id": 1,
  "titulo": "Como usar Spring Boot?",
  "mensagem": "Estou com dúvida...",
  "dataCriacao": "2026-01-18T10:30:00",
  "status": "NAO_RESPONDIDO",
  "autor": "João Silva",
  "curso": "Spring Boot Iniciante"
}
```

**Response** (404 Not Found):

```json
{
  "status": 404,
  "mensagem": "Tópico com ID 999 não encontrado",
  "timestamp": "18/01/2026 10:35:00",
  "path": "/api/topicos/999",
  "erros": null
}
```

### 4. Atualizar um Tópico

**PUT** `/api/topicos/{id}`

**Request Body** (todos os campos são opcionais):

```json
{
  "titulo": "Como usar Spring Boot? [Resolvido]",
  "mensagem": "Agora consegui resolver minha dúvida!",
  "status": "SOLUCIONADO"
}
```

**Response** (200 OK):

```json
{
  "id": 1,
  "titulo": "Como usar Spring Boot? [Resolvido]",
  "mensagem": "Agora consegui resolver minha dúvida!",
  "dataCriacao": "2026-01-18T10:30:00",
  "status": "SOLUCIONADO",
  "autor": "João Silva",
  "curso": "Spring Boot Iniciante"
}
```

### 5. Deletar um Tópico

**DELETE** `/api/topicos/{id}`

**Response** (204 No Content)

- Sem corpo na resposta

**Response** (404 Not Found):

```json
{
  "status": 404,
  "mensagem": "Tópico com ID 999 não encontrado",
  "timestamp": "18/01/2026 10:35:00",
  "path": "/api/topicos/999",
  "erros": null
}
```

## ✔️ Validações Implementadas

### Ao Criar um Tópico:

- ✅ Título é obrigatório (5-100 caracteres)
- ✅ Mensagem é obrigatória (mínimo 10 caracteres)
- ✅ Autor é obrigatório (3-100 caracteres)
- ✅ Curso é obrigatório (3-100 caracteres)
- ✅ Título e mensagem não podem ser duplicados
- ✅ Status começa automaticamente como `NAO_RESPONDIDO`

### Ao Atualizar um Tópico:

- ✅ Todos os campos são opcionais
- ✅ Se fornecido, título deve ter 5-100 caracteres
- ✅ Se fornecido, mensagem deve ter mínimo 10 caracteres
- ✅ Status pode ser um dos: NAO_RESPONDIDO, NAO_SOLUCIONADO, SOLUCIONADO, FECHADO

## 🔍 Tratamento de Erros

### Status HTTP Retornados:

- **200 OK**: Requisição bem-sucedida (GET, PUT)
- **201 Created**: Recurso criado com sucesso (POST)
- **204 No Content**: Recurso deletado com sucesso (DELETE)
- **400 Bad Request**: Erro de validação nos dados
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Tópico duplicado (mesmo título e mensagem)
- **500 Internal Server Error**: Erro interno do servidor

### Exemplo de Resposta de Erro:

```json
{
  "status": 400,
  "mensagem": "Erro de validação nos dados enviados",
  "timestamp": "18/01/2026 10:35:00",
  "path": "/api/topicos",
  "erros": [
    {
      "campo": "titulo",
      "mensagem": "Título é obrigatório"
    },
    {
      "campo": "mensagem",
      "mensagem": "Mensagem deve ter no mínimo 10 caracteres"
    }
  ]
}
```

## 🔄 Enums e Valores Válidos

### StatusTopico

- `NAO_RESPONDIDO`: Tópico ainda não possui respostas
- `NAO_SOLUCIONADO`: Tópico possui respostas, mas não foi resolvido
- `SOLUCIONADO`: Tópico foi resolvido
- `FECHADO`: Tópico foi fechado pelo moderador

## 💾 Configuração do Banco de Dados

### Desenvolvimento (H2)

O projeto vem pré-configurado para usar H2 (banco em memória), ideal para desenvolvimento e testes.

**Arquivo**: `application.properties`

```properties
spring.datasource.url=jdbc:h2:mem:forumdb
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Produção (MySQL)

Para usar MySQL em produção, configure:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumdb
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.flyway.enabled=true
```

## 📚 Recursos Adicionais

### Logging

A aplicação utiliza SLF4J com logs em diferentes níveis:

- **DEBUG**: Detalhes de requisições HTTP e operações do Hibernate
- **INFO**: Operações principais do negócio
- **WARN**: Situações anormais (recurso não encontrado, duplicatas)
- **ERROR**: Erros não tratados

Configure em `application.properties`:

```properties
logging.level.br.com.alura=DEBUG
logging.level.org.springframework.web=DEBUG
```

### Paginação

Todos os endpoints GET que retornam listas suportam paginação:

```bash
GET /api/topicos?page=0&size=10&sort=dataCriacao,desc
```

Parâmetros:

- `page`: Número da página (começa em 0)
- `size`: Quantidade de registros por página
- `sort`: Campo e direção de ordenação (field,asc ou field,desc)

## 🚧 Próximos Passos (Sprint 2+)

- [ ] Implementar autenticação JWT
- [ ] Criar entidade Usuario
- [ ] Criar entidade Resposta
- [ ] Implementar respostas a tópicos
- [ ] Adicionar testes automatizados
- [ ] Documentação com Swagger/OpenAPI
- [ ] Implementar cache com Redis
- [ ] Adicionar rate limiting

## 📄 Licença

Este projeto é parte do Challenge ONE da Alura.

## 👨‍💼 Autor

Desenvolvido como parte do Challenge ONE - Alura Fórum Hub.

---

**Dúvidas ou sugestões?** Abra uma issue no repositório do projeto.

# 🔐 Configuração de Variáveis de Ambiente - Forum Hub API

## 📋 Sumário

Este documento descreve como configurar variáveis de ambiente para a aplicação Forum Hub.

## 🚀 Primeiros Passos

### 1. Copiar arquivo de exemplo

```bash
cp .env.example .env
```

### 2. Editar arquivo .env

```bash
# Linux/Mac
nano .env

# Windows (VS Code)
code .env

# Windows (Notepad)
notepad .env
```

### 3. Configurar valores (veja seções abaixo)

### 4. Nunca commitar .env

O arquivo `.env` está no `.gitignore` para evitar expor credenciais:

```bash
# Verificar que está ignorado
git status  # não deve aparecer .env
```

---

## 🔧 Variáveis de Ambiente

### Servidor

#### `SERVER_PORT`

- **Tipo**: Integer
- **Padrão**: `8080`
- **Descrição**: Porta onde a aplicação rodará
- **Exemplo**: `SERVER_PORT=8080`

#### `SERVER_CONTEXT_PATH`

- **Tipo**: String
- **Padrão**: `/api`
- **Descrição**: Prefixo do caminho (URL raiz)
- **Exemplo**: `SERVER_CONTEXT_PATH=/api`
- **Nota**: URL final será `http://localhost:8080/api`

---

### Banco de Dados

#### `DATABASE_URL`

- **Tipo**: String (JDBC URL)
- **Padrão**: `jdbc:mysql://localhost:3306/forum?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true`
- **Descrição**: URL de conexão com o banco MySQL
- **Exemplo (Local)**: `DATABASE_URL=jdbc:mysql://localhost:3306/forum?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true`
- **Exemplo (Remoto)**: `DATABASE_URL=jdbc:mysql://db.example.com:3306/forum?useSSL=true&serverTimezone=America/Sao_Paulo`

#### `DATABASE_USERNAME`

- **Tipo**: String
- **Padrão**: `root`
- **Descrição**: Usuário do banco de dados
- **⚠️ Sensível**: Mude em produção
- **Exemplo**: `DATABASE_USERNAME=forum_user`

#### `DATABASE_PASSWORD`

- **Tipo**: String
- **Padrão**: `gpm123`
- **Descrição**: Senha do banco de dados
- **⚠️ CRÍTICO**: Mude em produção
- **Exemplo**: `DATABASE_PASSWORD=SenhaForte@123`

#### `DATABASE_DRIVER`

- **Tipo**: String
- **Padrão**: `com.mysql.cj.jdbc.Driver`
- **Descrição**: Driver JDBC
- **Nota**: Não alterar normalmente

---

### JPA / Hibernate

#### `JPA_DATABASE_PLATFORM`

- **Tipo**: String
- **Padrão**: `org.hibernate.dialect.MySQL8Dialect`
- **Descrição**: Dialect do Hibernate para o banco
- **Valores válidos**:
  - `org.hibernate.dialect.MySQL8Dialect` (MySQL 8.0+)
  - `org.hibernate.dialect.MySQL5InnoDBDialect` (MySQL 5.x)

#### `JPA_DDL_AUTO`

- **Tipo**: String
- **Padrão**: `validate`
- **Descrição**: Estratégia de criação de tabelas
- **⚠️ Cuidado**: Não use `create` ou `create-drop` em produção!
- **Valores válidos**:
  - `validate` (apenas valida) - **PRODUÇÃO**
  - `create` (cria tabelas) - **DESENVOLVIMENTO**
  - `create-drop` (cria e deleta) - **TESTES**
  - `update` (atualiza incrementalmente)
  - `none` (nenhuma operação)

#### `JPA_SHOW_SQL`

- **Tipo**: Boolean
- **Padrão**: `false`
- **Descrição**: Mostrar SQL gerado em logs
- **Produção**: Deixar `false` para melhor performance

#### `JPA_FORMAT_SQL`

- **Tipo**: Boolean
- **Padrão**: `true`
- **Descrição**: Formatar SQL em logs
- **Nota**: Apenas útil se `JPA_SHOW_SQL=true`

#### `JPA_USE_SQL_COMMENTS`

- **Tipo**: Boolean
- **Padrão**: `true`
- **Descrição**: Incluir comentários SQL em logs
- **Nota**: Apenas útil se `JPA_SHOW_SQL=true`

---

### Flyway (Migrações)

#### `FLYWAY_ENABLED`

- **Tipo**: Boolean
- **Padrão**: `true`
- **Descrição**: Habilitar/desabilitar Flyway
- **Produção**: Deve estar `true`

#### `FLYWAY_BASELINE`

- **Tipo**: Boolean
- **Padrão**: `true`
- **Descrição**: Criar baseline em banco existente
- **Primeira vez**: Deve estar `true`

#### `FLYWAY_LOCATIONS`

- **Tipo**: String
- **Padrão**: `classpath:db/migration`
- **Descrição**: Diretório com arquivos de migração
- **Nota**: Não alterar normalmente

#### `FLYWAY_BASELINE_VERSION`

- **Tipo**: String
- **Padrão**: `0`
- **Descrição**: Versão baseline
- **Nota**: Não alterar normalmente

---

### Logging

#### `LOG_LEVEL_ROOT`

- **Tipo**: String
- **Padrão**: `INFO`
- **Descrição**: Nível de log global
- **Produção**: `WARN` ou `ERROR`
- **Valores**: `OFF`, `FATAL`, `ERROR`, `WARN`, `INFO`, `DEBUG`, `TRACE`

#### `LOG_LEVEL_APP`

- **Tipo**: String
- **Padrão**: `DEBUG`
- **Descrição**: Nível de log da aplicação
- **Produção**: `INFO`
- **Package**: `br.com.alura.forum`

#### `LOG_LEVEL_WEB`

- **Tipo**: String
- **Padrão**: `DEBUG`
- **Descrição**: Nível de log do Spring Web
- **Produção**: `WARN`

#### `LOG_LEVEL_SQL`

- **Tipo**: String
- **Padrão**: `DEBUG`
- **Descrição**: Nível de log do Hibernate SQL
- **Produção**: `OFF` ou `ERROR`

#### `LOG_LEVEL_BINDER`

- **Tipo**: String
- **Padrão**: `TRACE`
- **Descrição**: Nível de log dos parâmetros SQL
- **Produção**: `OFF`

---

### 🔐 Segurança - JWT

#### `JWT_SECRET`

- **Tipo**: String
- **Descrição**: Chave secreta para assinar tokens JWT
- **🔴 CRÍTICO**: Nunca exponha em logs ou repositório
- **Requisitos**:
  - Mínimo 256 bits (32 caracteres)
  - Alterar em cada ambiente
  - Usar caracteres alfanuméricos + especiais
- **Como gerar** (Linux/Mac):
  ```bash
  openssl rand -base64 32
  # Copiar output para JWT_SECRET
  ```
- **Como gerar** (Windows PowerShell):
  ```powershell
  [Convert]::ToBase64String((New-Object System.Random).GetBytes(32))
  ```
- **Exemplo**:
  ```
  JWT_SECRET=aB1cD2eF3gH4iJ5kL6mN7oP8qR9sT0uV1wX2yZ3
  ```

#### `JWT_ISSUER`

- **Tipo**: String
- **Padrão**: `Forum Hub`
- **Descrição**: Identificação da aplicação no token
- **Exemplo**: `JWT_ISSUER=Forum Hub API`

#### `JWT_EXPIRATION_HOURS`

- **Tipo**: Integer
- **Padrão**: `24`
- **Descrição**: Tempo de expiração do token em horas
- **Produção**: Considere valores menores (2-6 horas)
- **Exemplo**: `JWT_EXPIRATION_HOURS=2`

---

## 🖥️ Configuração por Ambiente

### 💻 Desenvolvimento

**.env (desenvolvimento)**

```properties
SERVER_PORT=8080
DATABASE_URL=jdbc:mysql://localhost:3306/forum?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
DATABASE_USERNAME=root
DATABASE_PASSWORD=gpm123
JPA_DDL_AUTO=validate
JPA_SHOW_SQL=false
LOG_LEVEL_APP=DEBUG
JWT_SECRET=chave-segura-desenvolvimento-minimo-32-caracteres-aleatoria
JWT_EXPIRATION_HOURS=24
```

### 🧪 Testes/Staging

**.env (testes)**

```properties
SERVER_PORT=8080
DATABASE_URL=jdbc:mysql://db-staging.example.com:3306/forum_test?useSSL=true
DATABASE_USERNAME=forum_staging
DATABASE_PASSWORD=SenhaForte@Staging123
JPA_DDL_AUTO=validate
JPA_SHOW_SQL=false
LOG_LEVEL_APP=INFO
JWT_SECRET=chave-secreta-staging-muito-forte-256-bits-aleatorio
JWT_EXPIRATION_HOURS=6
```

### 🚀 Produção

**.env (produção)**

```properties
SERVER_PORT=8080
DATABASE_URL=jdbc:mysql://db-prod.example.com:3306/forum?useSSL=true&serverTimezone=America/Sao_Paulo
DATABASE_USERNAME=forum_prod_user
DATABASE_PASSWORD=SenhaProducaoForte@#$%^&*
JPA_DDL_AUTO=validate
JPA_SHOW_SQL=false
LOG_LEVEL_ROOT=WARN
LOG_LEVEL_APP=INFO
LOG_LEVEL_WEB=WARN
LOG_LEVEL_SQL=OFF
LOG_LEVEL_BINDER=OFF
JWT_SECRET=chave-secreta-producao-super-forte-aleatorio-256-bits-minimo
JWT_EXPIRATION_HOURS=4
```

---

## 📱 Variáveis de Ambiente no Sistema

### Linux/Mac

#### Temporária (apenas sessão atual)

```bash
export JWT_SECRET="sua-chave-secreta"
export DATABASE_USERNAME="root"
export DATABASE_PASSWORD="gpm123"
```

#### Permanente (editar ~/.bashrc ou ~/.zshrc)

```bash
# Adicionar no final do arquivo
export JWT_SECRET="sua-chave-secreta"
export DATABASE_USERNAME="root"
export DATABASE_PASSWORD="gpm123"

# Depois:
source ~/.bashrc  # ou source ~/.zshrc
```

### Windows (PowerShell)

#### Temporária

```powershell
$env:JWT_SECRET="sua-chave-secreta"
$env:DATABASE_USERNAME="root"
$env:DATABASE_PASSWORD="gpm123"
```

#### Permanente

```powershell
# Como Administrator:
[Environment]::SetEnvironmentVariable("JWT_SECRET", "sua-chave-secreta", "User")
[Environment]::SetEnvironmentVariable("DATABASE_USERNAME", "root", "User")
[Environment]::SetEnvironmentVariable("DATABASE_PASSWORD", "gpm123", "User")

# Fechar e reabrir PowerShell para efetuar
```

### Windows (CMD)

```cmd
setx JWT_SECRET "sua-chave-secreta"
setx DATABASE_USERNAME "root"
setx DATABASE_PASSWORD "gpm123"

# Fechar e reabrir CMD para efetuar
```

---

## 🐳 Docker / Docker Compose

### Usando arquivo .env com Docker Compose

**docker-compose.yml**

```yaml
version: "3.8"

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - DATABASE_URL=${DATABASE_URL}
      - DATABASE_USERNAME=${DATABASE_USERNAME}
      - DATABASE_PASSWORD=${DATABASE_PASSWORD}
      - JWT_SECRET=${JWT_SECRET}
    depends_on:
      - db

  db:
    image: mysql:8.0
    environment:
      - MYSQL_DATABASE=${MYSQL_DATABASE:-forum}
      - MYSQL_ROOT_PASSWORD=${DATABASE_PASSWORD}
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

### Executar com .env

```bash
docker-compose --env-file .env up
```

---

## ☁️ Variáveis em Plataformas Cloud

### Heroku

```bash
heroku config:set JWT_SECRET="sua-chave-secreta"
heroku config:set DATABASE_URL="jdbc:mysql://..."
heroku config:set DATABASE_USERNAME="root"
heroku config:set DATABASE_PASSWORD="gpm123"

# Verificar
heroku config
```

### AWS (Systems Manager Parameter Store)

```bash
aws ssm put-parameter \
  --name /forum-hub/jwt-secret \
  --value "sua-chave-secreta" \
  --type "SecureString"
```

### Google Cloud Run

```bash
gcloud run deploy forum-hub \
  --set-env-vars JWT_SECRET="sua-chave-secreta" \
  --set-env-vars DATABASE_URL="..." \
  # ... outros params
```

---

## ✅ Checklist de Segurança

### Antes de Fazer Deploy

- [ ] Arquivo `.env` NÃO está em git (verificar com `git status`)
- [ ] Todas as credenciais foram alteradas
- [ ] `JWT_SECRET` tem mínimo 256 bits
- [ ] `DATABASE_PASSWORD` é uma senha forte
- [ ] `JPA_DDL_AUTO` está em `validate`
- [ ] `LOG_LEVEL_*` estão apropriados (`WARN` ou `ERROR` em produção)
- [ ] `JWT_EXPIRATION_HOURS` é razoável (não > 24h)
- [ ] Arquivo `.env.example` não contém valores reais

### Rotina

- [ ] Rotacionar `JWT_SECRET` periodicamente
- [ ] Monitorar logs para acessos não autorizados
- [ ] Atualizar dependências regularmente
- [ ] Fazer backup das credenciais de forma segura

---

## 🆘 Troubleshooting

### Erro: "Cannot load driver class: com.mysql.cj.jdbc.Driver"

**Causa**: MySQL não está instalado ou não está rodando.

**Solução**:

```bash
# Verificar se MySQL está rodando
mysql -u root -p

# Se não funcionar, instale MySQL
# macOS: brew install mysql
# Linux: sudo apt-get install mysql-server
# Windows: https://dev.mysql.com/downloads/mysql/
```

---

### Erro: "Access denied for user 'root'@'localhost'"

**Causa**: `DATABASE_USERNAME` ou `DATABASE_PASSWORD` incorretos.

**Solução**:

```bash
# Testar credenciais
mysql -u root -p  # digitar senha do .env

# Se falhar, resetar MySQL
# (varia por SO)
```

---

### Erro: "Cannot resolve property 'security.token.secret'"

**Causa**: Spring não consegue resolver a variável `${JWT_SECRET}`.

**Solução**:

1. Verificar se `.env` está no diretório raiz
2. Se não funcionar, usar variáveis de ambiente do SO
3. Verificar se aplicação foi reiniciada após mudança

---

## 📚 Recursos

- [Spring Boot Configuration Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
- [12 Factor App - Config](https://12factor.net/config)
- [Environment Variables Best Practices](https://12factor.net/config)

---

**Última atualização**: Janeiro 2026

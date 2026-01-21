#!/bin/bash

# ============================================================
# Forum Hub API - Script de Setup
# ============================================================
# 
# Este script configura o ambiente para desenvolvimento
# 
# Uso: bash setup.sh
#
# ============================================================

set -e  # Parar em caso de erro

echo "╔══════════════════════════════════════════════════════════╗"
echo "║       FORUM HUB API - SETUP PARA DESENVOLVIMENTO         ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

# ============================================================
# 1. Verificar pré-requisitos
# ============================================================

echo "📋 Verificando pré-requisitos..."
echo ""

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "❌ Java não está instalado!"
    echo "   Instale Java 17 ou superior em: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
echo "✅ Java $JAVA_VERSION encontrado"

# Verificar Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não está instalado!"
    echo "   Instale Maven em: https://maven.apache.org/download.cgi"
    exit 1
fi

echo "✅ Maven encontrado"

# Verificar MySQL
if ! command -v mysql &> /dev/null; then
    echo "⚠️  MySQL não está no PATH"
    echo "   Você pode executar manualmente ou instalar MySQL"
else
    echo "✅ MySQL encontrado"
fi

echo ""

# ============================================================
# 2. Criar arquivo .env
# ============================================================

echo "🔐 Configurando variáveis de ambiente..."
echo ""

if [ -f .env ]; then
    echo "⚠️  Arquivo .env já existe"
    read -p "Deseja sobrescrever? (s/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Ss]$ ]]; then
        echo "Pulando criação de .env"
    else
        cp .env.example .env
        echo "✅ Arquivo .env criado de .env.example"
    fi
else
    cp .env.example .env
    echo "✅ Arquivo .env criado de .env.example"
fi

echo ""

# ============================================================
# 3. Solicitar configurações sensíveis
# ============================================================

echo "⚙️  Configurar valores sensíveis"
echo ""

read -p "  📝 Usuário do banco de dados (padrão: root): " DB_USER
DB_USER=${DB_USER:-root}

read -sp "  🔐 Senha do banco de dados (padrão: gpm123): " DB_PASS
DB_PASS=${DB_PASS:-gpm123}
echo ""

read -sp "  🔐 JWT Secret (será gerado se deixar em branco): " JWT_SECRET
echo ""

# Se não forneceu JWT_SECRET, gerar um
if [ -z "$JWT_SECRET" ]; then
    if command -v openssl &> /dev/null; then
        JWT_SECRET=$(openssl rand -base64 32)
        echo "  ✅ JWT Secret gerado automaticamente"
    else
        JWT_SECRET="change-me-in-production-with-strong-secret-key-256bits"
        echo "  ⚠️  Usando JWT Secret padrão"
    fi
fi

echo ""

# ============================================================
# 4. Atualizar arquivo .env
# ============================================================

echo "💾 Atualizando arquivo .env..."

# Usar sed para atualizar variáveis (funciona em Linux/Mac)
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    sed -i '' "s|DATABASE_USERNAME=.*|DATABASE_USERNAME=$DB_USER|" .env
    sed -i '' "s|DATABASE_PASSWORD=.*|DATABASE_PASSWORD=$DB_PASS|" .env
    sed -i '' "s|JWT_SECRET=.*|JWT_SECRET=$JWT_SECRET|" .env
else
    # Linux
    sed -i "s|DATABASE_USERNAME=.*|DATABASE_USERNAME=$DB_USER|" .env
    sed -i "s|DATABASE_PASSWORD=.*|DATABASE_PASSWORD=$DB_PASS|" .env
    sed -i "s|JWT_SECRET=.*|JWT_SECRET=$JWT_SECRET|" .env
fi

echo "✅ Arquivo .env atualizado"
echo ""

# ============================================================
# 5. Criar banco de dados MySQL
# ============================================================

echo "🗄️  Criar banco de dados (opcional)"
echo ""

if command -v mysql &> /dev/null; then
    read -p "Deseja criar o banco de dados 'forum'? (s/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Ss]$ ]]; then
        read -sp "Senha do MySQL para $DB_USER: " MYSQL_PASS
        echo ""
        
        mysql -u "$DB_USER" -p"$MYSQL_PASS" -e "CREATE DATABASE IF NOT EXISTS forum;" 2>/dev/null && echo "✅ Banco de dados criado" || echo "⚠️  Erro ao criar banco (pode ser normal)"
    fi
else
    echo "⚠️  MySQL não encontrado no PATH"
    echo "   Execute manualmente:"
    echo "   mysql -u $DB_USER -p"
    echo "   CREATE DATABASE forum;"
fi

echo ""

# ============================================================
# 6. Instalar dependências Maven
# ============================================================

echo "📦 Instalando dependências Maven..."
echo ""

if mvn clean install -q -DskipTests; then
    echo "✅ Dependências instaladas"
else
    echo "❌ Erro ao instalar dependências"
    exit 1
fi

echo ""

# ============================================================
# 7. Resumo e próximos passos
# ============================================================

echo "╔══════════════════════════════════════════════════════════╗"
echo "║                  SETUP CONCLUÍDO! ✅                     ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

echo "📝 Próximos passos:"
echo ""
echo "1️⃣  Executar a aplicação:"
echo "    mvn spring-boot:run"
echo ""
echo "2️⃣  Testar a API:"
echo "    curl -X POST http://localhost:8080/login \\"
echo "      -H 'Content-Type: application/json' \\"
echo "      -d '{\"login\":\"admin@forum.com\",\"senha\":\"123456\"}'"
echo ""
echo "3️⃣  Para mais informações, veja:"
echo "    - README.md"
echo "    - ENVIRONMENT.md"
echo ""
echo "⚠️  Lembrete: Não commitar arquivo .env com credenciais!"
echo ""

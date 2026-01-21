@echo off
REM ============================================================
REM Forum Hub API - Script de Setup para Windows
REM ============================================================
REM 
REM Este script configura o ambiente para desenvolvimento
REM 
REM Uso: setup.bat
REM
REM ============================================================

setlocal enabledelayedexpansion

echo.
echo ╔══════════════════════════════════════════════════════════╗
echo ║       FORUM HUB API - SETUP PARA DESENVOLVIMENTO         ║
echo ╚══════════════════════════════════════════════════════════╝
echo.

REM ============================================================
REM 1. Verificar pré-requisitos
REM ============================================================

echo 📋 Verificando pré-requisitos...
echo.

REM Verificar Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Java não está instalado!
    echo    Instale Java 17 ou superior em: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
)
echo ✅ Java !JAVA_VERSION! encontrado
echo.

REM Verificar Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Maven não está instalado!
    echo    Instale Maven em: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

echo ✅ Maven encontrado
echo.

REM ============================================================
REM 2. Criar arquivo .env
REM ============================================================

echo 🔐 Configurando variáveis de ambiente...
echo.

if exist .env (
    echo ⚠️  Arquivo .env já existe
    set /p OVERWRITE="Deseja sobrescrever? (s/n): "
    if /i "!OVERWRITE!"=="s" (
        copy .env.example .env >nul
        echo ✅ Arquivo .env criado de .env.example
    ) else (
        echo Pulando criação de .env
    )
) else (
    copy .env.example .env >nul
    echo ✅ Arquivo .env criado de .env.example
)

echo.

REM ============================================================
REM 3. Solicitar configurações sensíveis
REM ============================================================

echo ⚙️  Configurar valores sensíveis
echo.

set /p DB_USER="  📝 Usuário do banco de dados (padrão: root): "
if "!DB_USER!"=="" set DB_USER=root

set /p DB_PASS="  🔐 Senha do banco de dados (padrão: gpm123): "
if "!DB_PASS!"=="" set DB_PASS=gpm123

set /p JWT_SECRET="  🔐 JWT Secret (será gerado se deixar em branco): "
if "!JWT_SECRET!"=="" (
    REM Gerar JWT_SECRET aleatório usando PowerShell
    for /f %%i in ('powershell -Command "[Convert]::ToBase64String((New-Object System.Random).GetBytes(32))"') do (
        set JWT_SECRET=%%i
    )
    echo ✅ JWT Secret gerado automaticamente
)

echo.

REM ============================================================
REM 4. Atualizar arquivo .env
REM ============================================================

echo 💾 Atualizando arquivo .env...

setlocal disabledelayedexpansion

REM Usar PowerShell para atualizar arquivo
powershell -Command ^
    "(Get-Content .env) -replace 'DATABASE_USERNAME=.*', 'DATABASE_USERNAME=%DB_USER%' | Set-Content .env" 

powershell -Command ^
    "(Get-Content .env) -replace 'DATABASE_PASSWORD=.*', 'DATABASE_PASSWORD=%DB_PASS%' | Set-Content .env" 

setlocal enabledelayedexpansion

REM Escapar caracteres especiais do PowerShell para JWT_SECRET
set "JWT_SECRET=!JWT_SECRET:\=\\!"
set "JWT_SECRET=!JWT_SECRET:"=\"!"

powershell -Command ^
    "(Get-Content .env) -replace 'JWT_SECRET=.*', 'JWT_SECRET=!JWT_SECRET!' | Set-Content .env"

echo ✅ Arquivo .env atualizado
echo.

REM ============================================================
REM 5. Instalar dependências Maven
REM ============================================================

echo 📦 Instalando dependências Maven...
echo.

call mvn clean install -q -DskipTests
if errorlevel 1 (
    echo ❌ Erro ao instalar dependências
    pause
    exit /b 1
)

echo ✅ Dependências instaladas
echo.

REM ============================================================
REM 6. Resumo e próximos passos
REM ============================================================

echo ╔══════════════════════════════════════════════════════════╗
echo ║                  SETUP CONCLUÍDO! ✅                     ║
echo ╚══════════════════════════════════════════════════════════╝
echo.

echo 📝 Próximos passos:
echo.
echo 1️⃣  Executar a aplicação:
echo     mvn spring-boot:run
echo.
echo 2️⃣  Testar a API:
echo     curl -X POST http://localhost:8080/login ^
echo       -H "Content-Type: application/json" ^
echo       -d "{\"login\":\"admin@forum.com\",\"senha\":\"123456\"}"
echo.
echo 3️⃣  Para mais informações, veja:
echo     - README.md
echo     - ENVIRONMENT.md
echo.
echo ⚠️  Lembrete: Não commitar arquivo .env com credenciais!
echo.

pause

-- Criar tabela de usuários para autenticação
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_usuarios_login (login)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
-- Inserir usuário padrão para testes (senha: 123456)
-- BCrypt hash do "123456"
INSERT INTO usuarios (login, senha)
VALUES (
        'admin@forum.com',
        '$2a$10$8NdWMjVkd8VMDX0HmlVDSu3Df8Zcr6f2KOx7YYHn3tqKyZ4NxVyvy'
    );
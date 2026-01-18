-- Criar tabela de tópicos com constraints e índices otimizados
CREATE TABLE topicos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL UNIQUE,
    mensagem LONGTEXT NOT NULL,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM(
        'NAO_RESPONDIDO',
        'NAO_SOLUCIONADO',
        'SOLUCIONADO',
        'FECHADO'
    ) NOT NULL DEFAULT 'NAO_RESPONDIDO',
    autor VARCHAR(100) NOT NULL,
    curso VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_topicos_mensagem (mensagem(100))
);
-- Índices para melhorar performance em buscas
CREATE INDEX idx_topicos_status ON topicos(status);
CREATE INDEX idx_topicos_autor ON topicos(autor);
CREATE INDEX idx_topicos_curso ON topicos(curso);
CREATE INDEX idx_topicos_data_criacao ON topicos(data_criacao DESC);
CREATE INDEX idx_topicos_titulo ON topicos(titulo);
-- Criar tabela de auditoria (opcional, para rastrear mudanças)
CREATE TABLE topicos_auditoria (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    topico_id BIGINT NOT NULL,
    acao VARCHAR(50) NOT NULL,
    dados_anteriores JSON,
    dados_novos JSON,
    usuario VARCHAR(100),
    data_modificacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (topico_id) REFERENCES topicos(id) ON DELETE CASCADE,
    INDEX idx_auditoria_topico_id (topico_id),
    INDEX idx_auditoria_data (data_modificacao DESC)
);
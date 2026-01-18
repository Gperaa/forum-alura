package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Classe principal da aplicação Spring Boot.
 * Inicializa o servidor e configura todos os componentes da aplicação.
 *
 * Porta: 8080
 * Context Path: /api
 *
 * Endpoints disponíveis:
 * - POST /api/topicos - Criar novo tópico
 * - GET /api/topicos - Listar tópicos
 * - GET /api/topicos/{id} - Obter tópico
 * - PUT /api/topicos/{id} - Atualizar tópico
 * - DELETE /api/topicos/{id} - Deletar tópico
 */
@SpringBootApplication
public class ForumHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumHubApplication.class, args);
    }
}

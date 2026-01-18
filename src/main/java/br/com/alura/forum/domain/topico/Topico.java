package br.com.alura.forum.domain.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidade que representa um tópico no fórum.
 * Um tópico é uma discussão iniciada por um usuário sobre um assunto
 * específico.
 */
@Entity
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTopico status;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(nullable = false, length = 100)
    private String curso;

    /**
     * Construtor para criar um novo tópico com dados básicos.
     */
    public Topico(String titulo, String mensagem, String autor, String curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = autor;
        this.curso = curso;
        this.status = StatusTopico.NAO_RESPONDIDO;
    }

    /**
     * Atualiza os dados do tópico permitidos (título, mensagem, status).
     */
    public void atualizar(String titulo, String mensagem, StatusTopico status) {
        if (titulo != null) {
            this.titulo = titulo;
        }
        if (mensagem != null) {
            this.mensagem = mensagem;
        }
        if (status != null) {
            this.status = status;
        }
    }
}

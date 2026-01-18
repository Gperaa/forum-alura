package br.com.alura.forum.dto.topico;

import br.com.alura.forum.domain.topico.StatusTopico;
import jakarta.validation.constraints.Size;

/**
 * DTO para atualização de um tópico existente.
 * Todos os campos são opcionais, permitindo atualizações parciais.
 */
public record AtualizacaoTopicoDto(
        @Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres") String titulo,

        @Size(min = 10, message = "Mensagem deve ter no mínimo 10 caracteres") String mensagem,

        StatusTopico status) {
}

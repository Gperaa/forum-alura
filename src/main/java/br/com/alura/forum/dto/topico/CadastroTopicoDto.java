package br.com.alura.forum.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para cadastro de um novo tópico.
 * Utiliza Java Records para uma sintaxe mais concisa e imutável.
 */
public record CadastroTopicoDto(
        @NotBlank(message = "Título é obrigatório") @Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres") String titulo,

        @NotBlank(message = "Mensagem é obrigatória") @Size(min = 10, message = "Mensagem deve ter no mínimo 10 caracteres") String mensagem,

        @NotBlank(message = "Autor é obrigatório") @Size(min = 3, max = 100, message = "Autor deve ter entre 3 e 100 caracteres") String autor,

        @NotBlank(message = "Curso é obrigatório") @Size(min = 3, max = 100, message = "Curso deve ter entre 3 e 100 caracteres") String curso) {
}

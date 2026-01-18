package br.com.alura.forum.dto.exception;

/**
 * DTO que representa um erro de validação em um campo específico.
 */
public record CampoErroDto(
        String campo,
        String mensagem
) {}

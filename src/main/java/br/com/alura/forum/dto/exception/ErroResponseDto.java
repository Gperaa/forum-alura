package br.com.alura.forum.dto.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO padrão para resposta de erro.
 */
public record ErroResponseDto(
        int status,
        String mensagem,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime timestamp,
        String path,
        List<CampoErroDto> erros) {
}

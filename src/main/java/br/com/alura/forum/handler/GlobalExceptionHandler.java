package br.com.alura.forum.handler;

import br.com.alura.forum.dto.exception.CampoErroDto;
import br.com.alura.forum.dto.exception.ErroResponseDto;
import br.com.alura.forum.exception.TopicoJaExisteException;
import br.com.alura.forum.exception.TopicoNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manipulador global de exceções para a aplicação.
 * Centraliza o tratamento de erros e fornece respostas padronizadas.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Trata exceções de validação do Spring.
     *
     * @param ex      a exceção de validação
     * @param request a requisição web
     * @return ResponseEntity com status 400 e detalhes dos erros
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDto> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        log.warn("Erro de validação: {}", ex.getMessage());

        List<CampoErroDto> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> new CampoErroDto(
                        erro.getField(),
                        erro.getDefaultMessage()))
                .collect(Collectors.toList());

        ErroResponseDto resposta = new ErroResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos dados enviados",
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                erros);

        return ResponseEntity.badRequest().body(resposta);
    }

    /**
     * Trata exceção quando um tópico não é encontrado.
     *
     * @param ex      a exceção
     * @param request a requisição web
     * @return ResponseEntity com status 404
     */
    @ExceptionHandler(TopicoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDto> handleTopicoNaoEncontrado(
            TopicoNaoEncontradoException ex,
            WebRequest request) {

        log.warn("Recurso não encontrado: {}", ex.getMessage());

        ErroResponseDto resposta = new ErroResponseDto(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    /**
     * Trata exceção quando tenta-se criar um tópico duplicado.
     *
     * @param ex      a exceção
     * @param request a requisição web
     * @return ResponseEntity com status 409 (Conflict)
     */
    @ExceptionHandler(TopicoJaExisteException.class)
    public ResponseEntity<ErroResponseDto> handleTopicoJaExiste(
            TopicoJaExisteException ex,
            WebRequest request) {

        log.warn("Conflito ao criar tópico: {}", ex.getMessage());

        ErroResponseDto resposta = new ErroResponseDto(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    /**
     * Trata exceções genéricas não capturadas.
     *
     * @param ex      a exceção
     * @param request a requisição web
     * @return ResponseEntity com status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDto> handleGenericException(
            Exception ex,
            WebRequest request) {

        log.error("Erro interno do servidor", ex);

        ErroResponseDto resposta = new ErroResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor. Entre em contato com o suporte.",
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}

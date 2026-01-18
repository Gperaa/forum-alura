package br.com.alura.forum.exception;

/**
 * Exceção lançada quando um tópico não é encontrado no banco de dados.
 */
public class TopicoNaoEncontradoException extends RuntimeException {

    public TopicoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public TopicoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

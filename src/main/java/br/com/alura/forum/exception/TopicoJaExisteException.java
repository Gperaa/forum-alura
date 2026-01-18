package br.com.alura.forum.exception;

/**
 * Exceção lançada quando tenta-se criar um tópico com título e mensagem
 * duplicados.
 */
public class TopicoJaExisteException extends RuntimeException {

    public TopicoJaExisteException(String mensagem) {
        super(mensagem);
    }

    public TopicoJaExisteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

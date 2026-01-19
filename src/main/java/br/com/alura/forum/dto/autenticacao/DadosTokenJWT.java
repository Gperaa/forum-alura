package br.com.alura.forum.dto.autenticacao;

/**
 * DTO que retorna o token JWT após autenticação bem-sucedida.
 * Utilizado na resposta do endpoint POST /login.
 */
public record DadosTokenJWT(
        String token,
        String tipo) {
    /**
     * Construtor conveniente que define o tipo como Bearer.
     *
     * @param token o token JWT
     * @return uma nova instância de DadosTokenJWT
     */
    public static DadosTokenJWT bearer(String token) {
        return new DadosTokenJWT(token, "Bearer");
    }
}

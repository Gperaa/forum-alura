package br.com.alura.forum.security;

import br.com.alura.forum.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Serviço responsável pela geração e validação de tokens JWT.
 * Utiliza a biblioteca auth0-java-jwt para operações com JWT.
 */
@Service
@Slf4j
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer:Forum Hub}")
    private String issuer;

    @Value("${api.security.token.expiration-hours:24}")
    private Integer expirationHours;

    /**
     * Gera um novo token JWT para um usuário autenticado.
     *
     * @param usuario o usuário autenticado
     * @return o token JWT gerado
     * @throws JWTCreationException em caso de erro ao criar o token
     */
    public String gerarToken(Usuario usuario) {
        try {
            log.debug("Gerando token JWT para usuário: {}", usuario.getLogin());

            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);

            log.info("Token JWT gerado com sucesso para usuário: {}", usuario.getLogin());
            return token;

        } catch (JWTCreationException exception) {
            log.error("Erro ao gerar token JWT: {}", exception.getMessage());
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    /**
     * Valida um token JWT e retorna o login do usuário.
     *
     * @param token o token JWT a ser validado
     * @return o login do usuário extraído do token, ou null se o token for inválido
     */
    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            log.warn("Token JWT inválido ou expirado: {}", exception.getMessage());
            return null;
        }
    }

    /**
     * Calcula a data de expiração do token.
     *
     * @return um Instant representando a data e hora de expiração
     */
    private Instant gerarDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(expirationHours)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}

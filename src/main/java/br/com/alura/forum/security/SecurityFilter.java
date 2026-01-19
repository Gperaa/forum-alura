package br.com.alura.forum.security;

import br.com.alura.forum.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de segurança customizado que intercepta todas as requisições HTTP.
 * Valida o token JWT presente no cabeçalho Authorization e autentica o usuário
 * no contexto do Spring Security.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Filtra cada requisição HTTP, extraindo e validando o token JWT.
     *
     * @param request     a requisição HTTP
     * @param response    a resposta HTTP
     * @param filterChain o chain de filtros
     * @throws ServletException em caso de erro no servlet
     * @throws IOException      em caso de erro de I/O
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Extrai o token JWT do cabeçalho Authorization
        String token = extrairToken(request);

        if (token != null) {
            // Valida o token e obtém o login do usuário
            String login = tokenService.validarToken(token);

            if (login != null) {
                log.debug("Token válido para usuário: {}", login);

                // Busca o usuário no banco de dados
                var usuario = usuarioRepository.findByLogin(login);

                if (usuario.isPresent()) {
                    // Cria um token de autenticação
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            usuario.get(),
                            null,
                            usuario.get().getAuthorities());

                    // Define a autenticação no contexto de segurança do Spring
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    log.debug("Autenticação estabelecida para usuário: {}", login);
                }
            } else {
                log.warn("Token inválido ou expirado na requisição");
            }
        }

        // Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token JWT do cabeçalho Authorization.
     * O token deve estar no formato: "Bearer <token>"
     *
     * @param request a requisição HTTP
     * @return o token JWT sem o prefixo "Bearer", ou null se não houver token
     *         válido
     */
    private String extrairToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring("Bearer ".length());
        }

        return null;
    }
}

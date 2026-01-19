package br.com.alura.forum.controller;

import br.com.alura.forum.dto.autenticacao.DadosAutenticacao;
import br.com.alura.forum.dto.autenticacao.DadosTokenJWT;
import br.com.alura.forum.domain.usuario.Usuario;
import br.com.alura.forum.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para autenticação de usuários.
 * Responsável pelo endpoint de login e geração de tokens JWT.
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * Autentica um usuário e retorna um token JWT.
     *
     * @param dados os dados de autenticação (login e senha)
     * @return ResponseEntity contendo o token JWT gerado
     */
    @PostMapping
    public ResponseEntity<DadosTokenJWT> autenticar(@Valid @RequestBody DadosAutenticacao dados) {
        log.info("Tentativa de autenticação para usuário: {}", dados.login());

        try {
            // Cria um token de autenticação com login e senha
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    dados.login(),
                    dados.senha());

            // Autentica o usuário usando o AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Extrai o usuário autenticado
            Usuario usuario = (Usuario) authentication.getPrincipal();

            // Gera o token JWT
            String token = tokenService.gerarToken(usuario);

            log.info("Autenticação bem-sucedida para usuário: {}", dados.login());

            // Retorna o token em um DTO com tipo "Bearer"
            return ResponseEntity.ok(DadosTokenJWT.bearer(token));

        } catch (Exception e) {
            log.warn("Falha na autenticação para usuário: {}", dados.login());
            return ResponseEntity.status(401).build();
        }
    }
}

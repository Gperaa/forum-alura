package br.com.alura.forum.service;

import br.com.alura.forum.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço de autenticação que implementa UserDetailsService do Spring Security.
 * Responsável por carregar os dados do usuário durante o processo de
 * autenticação.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Carrega o usuário pelo login (username) para autenticação.
     *
     * @param login o login do usuário
     * @return UserDetails contendo os dados do usuário
     * @throws UsernameNotFoundException se o usuário não for encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("Carregando usuário com login: {}", login);

        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado com login: {}", login);
                    return new UsernameNotFoundException("Usuário não encontrado: " + login);
                });
    }
}

package br.com.alura.forum.config;

import br.com.alura.forum.security.SecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração de segurança da aplicação.
 * Define as regras de autenticação, autorização e o filtro JWT.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    /**
     * Configura o filtro de segurança HTTP.
     * Define quais endpoints precisam de autenticação e qual é o comportamento
     * padrão.
     *
     * @param http o objeto HttpSecurity
     * @return o SecurityFilterChain configurado
     * @throws Exception em caso de erro na configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (não necessário para APIs REST stateless)
                .csrf(csrf -> csrf.disable())

                // Define a política de sessão como STATELESS (sem sessão)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configura as autoridades de acesso aos endpoints
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público ao endpoint de login
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        // Requer autenticação para todos os outros endpoints
                        .anyRequest().authenticated())

                // Adiciona o filtro JWT antes do filtro de autenticação padrão
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Exporta o AuthenticationManager como bean para ser usado nos controllers.
     *
     * @param configuration a configuração de autenticação
     * @return o AuthenticationManager
     * @throws Exception em caso de erro
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Exporta o PasswordEncoder (BCrypt) como bean.
     * Usado para codificar senhas durante o registro e comparação durante a
     * autenticação.
     *
     * @return um novo BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

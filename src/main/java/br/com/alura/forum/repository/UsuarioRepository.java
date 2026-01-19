package br.com.alura.forum.repository;

import br.com.alura.forum.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para acesso aos dados da entidade Usuario.
 * Fornece operações padrão de CRUD e consultas personalizadas.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo login (email).
     *
     * @param login o login do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<Usuario> findByLogin(String login);

    /**
     * Verifica se já existe um usuário com o login informado.
     *
     * @param login o login do usuário
     * @return true se existe, false caso contrário
     */
    boolean existsByLogin(String login);
}

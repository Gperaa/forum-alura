package br.com.alura.forum.repository;

import br.com.alura.forum.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para acesso aos dados da entidade Topico.
 * Fornece operações padrão de CRUD e consultas personalizadas.
 */
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    /**
     * Verifica se já existe um tópico com o mesmo título.
     * Usado para validar a regra de negócio de não permitir títulos duplicados.
     *
     * @param titulo o título do tópico
     * @return true se existe, false caso contrário
     */
    boolean existsByTitulo(String titulo);

    /**
     * Verifica se já existe um tópico com o mesmo título e mensagem.
     * Usado para validar a regra de negócio de não permitir duplicatas completas.
     *
     * @param titulo   o título do tópico
     * @param mensagem a mensagem do tópico
     * @return true se existe, false caso contrário
     */
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
            "FROM Topico t WHERE t.titulo = :titulo AND t.mensagem = :mensagem")
    boolean existsByTituloAndMensagem(@Param("titulo") String titulo,
            @Param("mensagem") String mensagem);

    /**
     * Busca um tópico pelo ID.
     *
     * @param id o id do tópico
     * @return Optional contendo o tópico se encontrado
     */
    @Override
    Optional<Topico> findById(Long id);
}

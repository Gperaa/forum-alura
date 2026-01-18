package br.com.alura.forum.dto.topico;

import br.com.alura.forum.domain.topico.StatusTopico;
import br.com.alura.forum.domain.topico.Topico;

import java.time.LocalDateTime;

/**
 * DTO para retorno dos detalhes de um tópico na resposta da API.
 * Contém todas as informações públicas do tópico.
 */
public record DetalhesTopicoDto(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso) {
    /**
     * Construtor factory que cria um DetalhesTopicoDto a partir de uma entidade
     * Topico.
     *
     * @param topico a entidade Topico
     * @return uma nova instância de DetalhesTopicoDto
     */
    public static DetalhesTopicoDto fromTopico(Topico topico) {
        return new DetalhesTopicoDto(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso());
    }
}

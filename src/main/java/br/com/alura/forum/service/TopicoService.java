package br.com.alura.forum.service;

import br.com.alura.forum.domain.topico.Topico;
import br.com.alura.forum.dto.topico.AtualizacaoTopicoDto;
import br.com.alura.forum.dto.topico.CadastroTopicoDto;
import br.com.alura.forum.dto.topico.DetalhesTopicoDto;
import br.com.alura.forum.exception.TopicoJaExisteException;
import br.com.alura.forum.exception.TopicoNaoEncontradoException;
import br.com.alura.forum.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que encapsula a lógica de negócio relacionada aos tópicos.
 * Responsável por validações, conversões entre DTOs e entidades,
 * e operações de CRUD.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TopicoService {

    private final TopicoRepository topicoRepository;

    /**
     * Cria um novo tópico após validação.
     *
     * @param dto os dados do tópico a ser criado
     * @return DetalhesTopicoDto com os dados do tópico criado
     * @throws TopicoJaExisteException se já existe um tópico com mesmo título e
     *                                 mensagem
     */
    public DetalhesTopicoDto criarTopico(CadastroTopicoDto dto) {
        log.info("Criando novo tópico com título: {}", dto.titulo());

        // Validação: verificar se já existe tópico com mesmo título e mensagem
        if (topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())) {
            log.warn("Tentativa de criar tópico duplicado: título='{}', mensagem='{}'",
                    dto.titulo(), dto.mensagem());
            throw new TopicoJaExisteException(
                    "Já existe um tópico com o mesmo título e mensagem");
        }

        // Criar nova entidade Topico
        Topico topico = new Topico(
                dto.titulo(),
                dto.mensagem(),
                dto.autor(),
                dto.curso());

        // Salvar no banco de dados
        Topico topicoSalvo = topicoRepository.save(topico);
        log.info("Tópico criado com sucesso. ID: {}", topicoSalvo.getId());

        return DetalhesTopicoDto.fromTopico(topicoSalvo);
    }

    /**
     * Busca todos os tópicos com paginação.
     *
     * @param pageable informações de paginação
     * @return página contendo os tópicos
     */
    @Transactional(readOnly = true)
    public Page<DetalhesTopicoDto> listarTopicos(Pageable pageable) {
        log.info("Listando tópicos. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        return topicoRepository.findAll(pageable)
                .map(DetalhesTopicoDto::fromTopico);
    }

    /**
     * Busca um tópico específico pelo ID.
     *
     * @param id o ID do tópico
     * @return DetalhesTopicoDto com os dados do tópico
     * @throws TopicoNaoEncontradoException se o tópico não existir
     */
    @Transactional(readOnly = true)
    public DetalhesTopicoDto obterTopicoById(Long id) {
        log.info("Buscando tópico com ID: {}", id);

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tópico não encontrado. ID: {}", id);
                    return new TopicoNaoEncontradoException(
                            "Tópico com ID " + id + " não encontrado");
                });

        return DetalhesTopicoDto.fromTopico(topico);
    }

    /**
     * Atualiza um tópico existente.
     *
     * @param id  o ID do tópico a ser atualizado
     * @param dto os novos dados do tópico
     * @return DetalhesTopicoDto com os dados atualizados
     * @throws TopicoNaoEncontradoException se o tópico não existir
     */
    public DetalhesTopicoDto atualizarTopico(Long id, AtualizacaoTopicoDto dto) {
        log.info("Atualizando tópico com ID: {}", id);

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tópico não encontrado para atualização. ID: {}", id);
                    return new TopicoNaoEncontradoException(
                            "Tópico com ID " + id + " não encontrado");
                });

        // Atualizar apenas os campos fornecidos
        topico.atualizar(dto.titulo(), dto.mensagem(), dto.status());

        Topico topicoAtualizado = topicoRepository.save(topico);
        log.info("Tópico atualizado com sucesso. ID: {}", topicoAtualizado.getId());

        return DetalhesTopicoDto.fromTopico(topicoAtualizado);
    }

    /**
     * Deleta um tópico existente.
     *
     * @param id o ID do tópico a ser deletado
     * @throws TopicoNaoEncontradoException se o tópico não existir
     */
    public void deletarTopico(Long id) {
        log.info("Deletando tópico com ID: {}", id);

        // Verificar se o tópico existe
        if (!topicoRepository.existsById(id)) {
            log.warn("Tentativa de deletar tópico inexistente. ID: {}", id);
            throw new TopicoNaoEncontradoException(
                    "Tópico com ID " + id + " não encontrado");
        }

        topicoRepository.deleteById(id);
        log.info("Tópico deletado com sucesso. ID: {}", id);
    }
}

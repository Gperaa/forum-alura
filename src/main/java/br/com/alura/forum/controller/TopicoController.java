package br.com.alura.forum.controller;

import br.com.alura.forum.dto.topico.AtualizacaoTopicoDto;
import br.com.alura.forum.dto.topico.CadastroTopicoDto;
import br.com.alura.forum.dto.topico.DetalhesTopicoDto;
import br.com.alura.forum.service.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gerenciar os endpoints relacionados a tópicos.
 * 
 * Endpoints disponíveis:
 * - POST /topicos - Criar novo tópico
 * - GET /topicos - Listar tópicos (paginado)
 * - GET /topicos/{id} - Obter detalhes de um tópico
 * - PUT /topicos/{id} - Atualizar um tópico
 * - DELETE /topicos/{id} - Deletar um tópico
 */
@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
@Slf4j
public class TopicoController {

    private final TopicoService topicoService;

    /**
     * POST /topicos
     * Cria um novo tópico no fórum.
     *
     * @param dto os dados do tópico a ser criado
     * @return ResponseEntity com status 201 e os dados do tópico criado
     */
    @PostMapping
    public ResponseEntity<DetalhesTopicoDto> criarTopico(
            @Valid @RequestBody CadastroTopicoDto dto) {

        log.info("Requisição POST /topicos recebida. Título: {}", dto.titulo());

        DetalhesTopicoDto topicoReposta = topicoService.criarTopico(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(topicoReposta);
    }

    /**
     * GET /topicos
     * Lista todos os tópicos com paginação.
     *
     * @param pageable informações de paginação (page, size, sort)
     * @return ResponseEntity com status 200 e lista paginada de tópicos
     */
    @GetMapping
    public ResponseEntity<Page<DetalhesTopicoDto>> listarTopicos(Pageable pageable) {
        log.info("Requisição GET /topicos recebida. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DetalhesTopicoDto> topicos = topicoService.listarTopicos(pageable);

        return ResponseEntity.ok(topicos);
    }

    /**
     * GET /topicos/{id}
     * Obtém os detalhes de um tópico específico.
     *
     * @param id o ID do tópico
     * @return ResponseEntity com status 200 e os dados do tópico, ou 404 se não
     *         encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> obterTopico(@PathVariable Long id) {
        log.info("Requisição GET /topicos/{} recebida", id);

        DetalhesTopicoDto topico = topicoService.obterTopicoById(id);

        return ResponseEntity.ok(topico);
    }

    /**
     * PUT /topicos/{id}
     * Atualiza um tópico existente.
     * Permite atualizar: título, mensagem e status.
     *
     * @param id  o ID do tópico a ser atualizado
     * @param dto os novos dados do tópico
     * @return ResponseEntity com status 200 e os dados atualizados, ou 404 se não
     *         encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> atualizarTopico(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoTopicoDto dto) {

        log.info("Requisição PUT /topicos/{} recebida", id);

        DetalhesTopicoDto topicoAtualizado = topicoService.atualizarTopico(id, dto);

        return ResponseEntity.ok(topicoAtualizado);
    }

    /**
     * DELETE /topicos/{id}
     * Deleta um tópico existente.
     *
     * @param id o ID do tópico a ser deletado
     * @return ResponseEntity com status 204 (No Content) após sucesso, ou 404 se
     *         não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTopico(@PathVariable Long id) {
        log.info("Requisição DELETE /topicos/{} recebida", id);

        topicoService.deletarTopico(id);

        return ResponseEntity.noContent().build();
    }
}

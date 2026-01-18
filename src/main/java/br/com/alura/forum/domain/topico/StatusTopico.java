package br.com.alura.forum.domain.topico;

/**
 * Enum que representa os possíveis status de um tópico no fórum.
 */
public enum StatusTopico {
    NAO_RESPONDIDO("Não respondido"),
    NAO_SOLUCIONADO("Não solucionado"),
    SOLUCIONADO("Solucionado"),
    FECHADO("Fechado");

    private final String descricao;

    StatusTopico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

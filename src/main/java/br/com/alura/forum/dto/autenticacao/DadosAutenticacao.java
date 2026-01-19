package br.com.alura.forum.dto.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO que recebe os dados de autenticação do usuário.
 * Utilizado no endpoint POST /login.
 */
public record DadosAutenticacao(
        @NotBlank(message = "Login é obrigatório") @Email(message = "Login deve ser um email válido") String login,

        @NotBlank(message = "Senha é obrigatória") @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres") String senha) {
}

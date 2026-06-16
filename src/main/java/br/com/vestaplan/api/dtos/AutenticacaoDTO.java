package br.com.vestaplan.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail com formato inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        String senha
) {
}
package br.com.vestaplan.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DentistaDTO (

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail deve ser válido.")
        String email,

        @NotBlank(message = "O CPF é obrigatório.")
        @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres.")
        String cpf,

        @NotBlank(message = "O CRO é obrigatório.")
        String cro

){
}

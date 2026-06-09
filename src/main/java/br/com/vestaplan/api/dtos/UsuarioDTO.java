package br.com.vestaplan.api.dtos;

import br.com.vestaplan.api.enums.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 6, max = 255)
        String nome,

        @NotBlank(message = "O CPF é obrigatório.")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O formato do e-mail é inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha,

        @NotNull(message = "O perfil de usuário é obrigatório.")
        PerfilUsuario perfil

) {
}

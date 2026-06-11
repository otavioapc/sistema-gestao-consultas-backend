package br.com.vestaplan.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record EspecialidadeDTO(

        @NotBlank(message = "O nome é obrigatório")
        String nome

) {
}

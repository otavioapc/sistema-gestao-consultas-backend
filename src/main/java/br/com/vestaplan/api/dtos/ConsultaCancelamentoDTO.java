package br.com.vestaplan.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record ConsultaCancelamentoDTO(

        @NotBlank(message = "O motivo do cancelamento é obrigatório.")
        String motivoCancelamento

) {
}

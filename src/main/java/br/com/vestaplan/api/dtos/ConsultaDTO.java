package br.com.vestaplan.api.dtos;

import br.com.vestaplan.api.enums.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaDTO(

        @NotNull(message = "O ID do paciente é obrigatório")
        Integer idPaciente,

        @NotNull(message = "O ID do dentista é obrigatório")
        Integer idDentista,

        @NotNull(message = "O ID do usuário é obrigatório")
        Integer idUsuario,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "A data de início é obrigatória")
        LocalDateTime dataInicio,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "A data de fim é obrigatória")
        LocalDateTime dataFim

) {
}

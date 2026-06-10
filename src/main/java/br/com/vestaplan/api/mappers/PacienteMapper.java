package br.com.vestaplan.api.mappers;

import br.com.vestaplan.api.dtos.PacienteDTO;
import br.com.vestaplan.api.entity.Paciente;
import org.springframework.stereotype.Service;

@Service
public class PacienteMapper {

    public Paciente toEntity(PacienteDTO dto) {

        Paciente paciente = new Paciente();

        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setEmail(dto.email());
        paciente.setTelefone(dto.telefone());

        return paciente;
    }

}

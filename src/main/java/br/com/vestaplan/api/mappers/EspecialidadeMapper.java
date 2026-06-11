package br.com.vestaplan.api.mappers;

import br.com.vestaplan.api.dtos.EspecialidadeDTO;
import br.com.vestaplan.api.entity.Especialidade;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadeMapper {

    public Especialidade toEntity(EspecialidadeDTO dto){

        Especialidade especialidade = new Especialidade();

        especialidade.setNome(dto.nome());

        return especialidade;

    }

}

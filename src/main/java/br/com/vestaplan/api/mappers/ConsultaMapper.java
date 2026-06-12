package br.com.vestaplan.api.mappers;

import br.com.vestaplan.api.dtos.ConsultaDTO;
import br.com.vestaplan.api.entity.Consulta;
import org.springframework.stereotype.Service;

@Service
public class ConsultaMapper {

    public Consulta toEntity(ConsultaDTO dto){

        Consulta consulta = new Consulta();

        consulta.setDescricao(dto.descricao());
        consulta.setDataInicio(dto.dataInicio());
        consulta.setDataFim(dto.dataFim());

        return consulta;

    }
}

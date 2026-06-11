package br.com.vestaplan.api.mappers;

import br.com.vestaplan.api.dtos.DentistaDTO;
import br.com.vestaplan.api.entity.Dentista;
import org.springframework.stereotype.Service;

@Service
public class DentistaMapper {

    public Dentista toEntity(DentistaDTO dto){

        Dentista dentista = new Dentista();

        dentista.setNome(dto.nome());
        dentista.setCpf(dto.cpf());
        dentista.setEmail(dto.email());
        dentista.setCro(dto.cro());

        return dentista;

    }

}

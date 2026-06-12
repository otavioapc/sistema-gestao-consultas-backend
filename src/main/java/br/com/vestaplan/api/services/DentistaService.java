package br.com.vestaplan.api.services;

import br.com.vestaplan.api.dtos.DentistaDTO;
import br.com.vestaplan.api.entity.Dentista;
import br.com.vestaplan.api.entity.Especialidade;
import br.com.vestaplan.api.exceptions.EntidadeNaoEncontradaException;
import br.com.vestaplan.api.exceptions.NegocioException;
import br.com.vestaplan.api.mappers.DentistaMapper;
import br.com.vestaplan.api.repositories.DentistaRepository;
import br.com.vestaplan.api.repositories.EspecialidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DentistaService {

    private final DentistaRepository dentistaRepository;
    private final DentistaMapper dentistaMapper;
    private final EspecialidadeRepository especialidadeRepository;

    public DentistaService(DentistaRepository dentistaRepository,
                           DentistaMapper dentistaMapper,
                           EspecialidadeRepository especialidadeRepository) {
        this.dentistaRepository = dentistaRepository;
        this.dentistaMapper = dentistaMapper;
        this.especialidadeRepository = especialidadeRepository;
    }

    public Dentista save(DentistaDTO dto) {

        if (dentistaRepository.existsByEmail(dto.email())) {
            throw new NegocioException("Este email já está cadastrado!");
        }

        if (dentistaRepository.existsByCpf(dto.cpf())) {
            throw new NegocioException("Este CPF já está cadastrado");
        }

        if (dentistaRepository.existsByCro(dto.cro())) {
            throw new NegocioException("Este CRO já está cadastrado");
        }

        Dentista dentistaEntity = dentistaMapper.toEntity(dto);

        if (dto.especialidadesId() != null && !dto.especialidadesId().isEmpty()) {
            List<Especialidade> especialidades = especialidadeRepository.findAllById(dto.especialidadesId());
            dentistaEntity.setEspecialidades(especialidades);
        }

        return dentistaRepository.save(dentistaEntity);
    }

    public List<Dentista> getDentistas() {
        return dentistaRepository.findAll();
    }

    public Dentista findById(Integer id) {
        return dentistaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Dentista com o ID " + id + " não foi encontrado!"));
    }

    public List<Dentista> findDentistaByName(String name) {
        return dentistaRepository.findDentistaByNomeContainingIgnoreCase(name);
    }

    @Transactional
    public Dentista update(Integer id, DentistaDTO dto) {

        Dentista atual = dentistaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Dentista com o ID " + id + " não foi encontrado!"));

        atual.setNome(dto.nome());
        atual.setCpf(dto.cpf());
        atual.setEmail(dto.email());
        atual.setCro(dto.cro());

        if (dto.especialidadesId() != null) {
            List<Especialidade> novasEspecialidades = especialidadeRepository.findAllById(dto.especialidadesId());
            atual.setEspecialidades(novasEspecialidades);
        }

        return dentistaRepository.save(atual);
    }

    @Transactional
    public void delete(Integer id) {

        if (!dentistaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Dentista não encontrado para exclusão!");
        }
        dentistaRepository.deleteById(id);
    }

}

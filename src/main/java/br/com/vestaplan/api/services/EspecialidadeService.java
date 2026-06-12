package br.com.vestaplan.api.services;

import br.com.vestaplan.api.dtos.EspecialidadeDTO;
import br.com.vestaplan.api.entity.Especialidade;
import br.com.vestaplan.api.exceptions.EntidadeNaoEncontradaException;
import br.com.vestaplan.api.exceptions.NegocioException;
import br.com.vestaplan.api.mappers.EspecialidadeMapper;
import br.com.vestaplan.api.repositories.EspecialidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;
    private final EspecialidadeMapper especialidadeMapper;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository, EspecialidadeMapper especialidadeMapper) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeMapper = especialidadeMapper;
    }

    public Especialidade save(EspecialidadeDTO dto) {
        if (especialidadeRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new NegocioException("Esta especialidade já está cadastrada!");
        }

        Especialidade entity = especialidadeMapper.toEntity(dto);
        return especialidadeRepository.save(entity);
    }

    public List<Especialidade> getEspecialidades() {
        return especialidadeRepository.findAll();
    }

    public Especialidade findById(Integer id) {
        return especialidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Especialidade com o ID " + id + " não encontrada!"));
    }

    public List<Especialidade> findByName(String nome) {
        return especialidadeRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Especialidade update(Integer id, EspecialidadeDTO dto) {
        Especialidade atual = especialidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Especialidade com o ID " + id + " não encontrada!"));

        atual.setNome(dto.nome());
        return especialidadeRepository.save(atual);
    }

    @Transactional
    public void delete(Integer id) {
        if (!especialidadeRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Especialidade não encontrada para exclusão!");
        }
        especialidadeRepository.deleteById(id);
    }
}

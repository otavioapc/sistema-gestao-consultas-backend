package br.com.vestaplan.api.services;

import br.com.vestaplan.api.dtos.PacienteDTO;
import br.com.vestaplan.api.entity.Paciente;
import br.com.vestaplan.api.exceptions.EntidadeNaoEncontradaException;
import br.com.vestaplan.api.exceptions.NegocioException;
import br.com.vestaplan.api.mappers.PacienteMapper;
import br.com.vestaplan.api.repositories.PacienteRepository;
import br.com.vestaplan.api.utils.TextoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    public Paciente save(PacienteDTO dto) {

        String nomeLimpo = TextoUtils.higienizarNome(dto.nome());
        String cpfLimpo = TextoUtils.limparMascaras(dto.cpf());
        String emailLimpo = dto.email().trim().toLowerCase();
        String telefoneLimpo = TextoUtils.limparMascaras(dto.telefone());

        if (pacienteRepository.existsByEmail(dto.email())) {
            throw new NegocioException("Este email já está cadastrado!");
        }

        if (pacienteRepository.existsByCpf(dto.cpf())) {
            throw new NegocioException("Este CPF já está cadastrado!");
        }

        Paciente pacienteEntity = pacienteMapper.toEntity(dto);

        pacienteEntity.setNome(nomeLimpo);
        pacienteEntity.setCpf(cpfLimpo);
        pacienteEntity.setEmail(emailLimpo);
        pacienteEntity.setTelefone(telefoneLimpo);

        return pacienteRepository.save(pacienteEntity);
    }

    public List<Paciente> getPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente findById(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Paciente com o ID " + id + " não foi encontrado!"));
    }

    public List<Paciente> findPacienteByName(String name) {
        return pacienteRepository.findPacienteByNomeContainingIgnoreCase(name);
    }

    @Transactional
    public Paciente update(Integer id, PacienteDTO dto) {

        Paciente atual = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Paciente com o ID " + id + " não foi encontrado!"));

        atual.setNome(TextoUtils.higienizarNome(dto.nome()));
        atual.setCpf(TextoUtils.limparMascaras(dto.cpf()));
        atual.setEmail(dto.email().trim().toLowerCase());
        atual.setTelefone(TextoUtils.limparMascaras(dto.telefone()));

        return pacienteRepository.save(atual);
    }

    @Transactional
    public void deleteById(Integer id) {

        if (!pacienteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Paciente não encontrado para exclusão!");
        }
        pacienteRepository.deleteById(id);
    }
}

package br.com.vestaplan.api.services;

import br.com.vestaplan.api.dtos.ConsultaCancelamentoDTO;
import br.com.vestaplan.api.dtos.ConsultaDTO;
import br.com.vestaplan.api.dtos.ConsultaUpdateDTO;
import br.com.vestaplan.api.entity.Consulta;
import br.com.vestaplan.api.entity.Dentista;
import br.com.vestaplan.api.entity.Paciente;
import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.enums.StatusConsulta;
import br.com.vestaplan.api.mappers.ConsultaMapper;
import br.com.vestaplan.api.repositories.ConsultaRepository;
import br.com.vestaplan.api.repositories.DentistaRepository;
import br.com.vestaplan.api.repositories.PacienteRepository;
import br.com.vestaplan.api.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final ConsultaMapper consultaMapper;
    private final PacienteRepository pacienteRepository;
    private final DentistaRepository dentistaRepository;
    private final UsuarioRepository usuarioRepository;

    public ConsultaService(ConsultaRepository consultaRepository, ConsultaMapper consultaMapper,
                           PacienteRepository pacienteRepository, DentistaRepository dentistaRepository,
                           UsuarioRepository usuarioRepository) {
        this.consultaRepository = consultaRepository;
        this.consultaMapper = consultaMapper;
        this.pacienteRepository = pacienteRepository;
        this.dentistaRepository = dentistaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Consulta save(ConsultaDTO dto){

        Paciente paciente = pacienteRepository.findById(dto.idPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));

        Dentista dentista = dentistaRepository.findById(dto.idDentista())
                .orElseThrow(() -> new RuntimeException("Dentista não encontrado!"));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Consulta consultaEntity = consultaMapper.toEntity(dto);

        consultaEntity.setPaciente(paciente);
        consultaEntity.setDentista(dentista);
        consultaEntity.setUsuario(usuario);

        return consultaRepository.save(consultaEntity);
    }

    public List<Consulta> getConsultas(){
        return consultaRepository.findAll();
    }

    public Consulta findById(Integer id){
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));
    }

    @Transactional
    public Consulta update(Integer id, ConsultaUpdateDTO dto){

        Consulta atual = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com o ID " + id + " não foi encontrado!"));

        Paciente paciente = pacienteRepository.findById(dto.idPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));
        Dentista dentista = dentistaRepository.findById(dto.idDentista())
                .orElseThrow(() -> new RuntimeException("Dentista não encontrado!"));
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        atual.setPaciente(paciente);
        atual.setDentista(dentista);
        atual.setUsuario(usuario);
        atual.setDescricao(dto.descricao());
        atual.setDataInicio(dto.dataInicio());
        atual.setDataFim(dto.dataFim());
        atual.setStatus(dto.status());

        return consultaRepository.save(atual);

    }

    @Transactional
    public void deleteById(Integer id) {
        if (!consultaRepository.existsById(id)) {
            throw new RuntimeException("Consulta não encontrada para exclusão!");
        }
        consultaRepository.deleteById(id);
    }

    @Transactional
    public Consulta cancelarConsulta(Integer id, ConsultaCancelamentoDTO dto) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta com o ID " + id + " não foi encontrada!"));

        if(consulta.getStatus() == StatusConsulta.FINALIZADA){
            throw new RuntimeException("Não é possível cancelar uma consulta que já foi finalizada!");
        }

        if(consulta.getStatus() == StatusConsulta.CANCELADA){
            throw new RuntimeException("Esta consulta já está cancelada!");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        consulta.setMotivoCancelamento(dto.motivoCancelamento());

        return consultaRepository.save(consulta);

    }

    public List<Consulta> findConsultasFiltradas(Integer idPaciente, Integer idUsuario, Integer idEspecialidade, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return consultaRepository.findConsultasFiltradas(idPaciente, idUsuario, idEspecialidade, dataInicio, dataFim);
    }

}

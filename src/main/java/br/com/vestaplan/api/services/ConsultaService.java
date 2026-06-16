package br.com.vestaplan.api.services;

import br.com.vestaplan.api.dtos.ConsultaCancelamentoDTO;
import br.com.vestaplan.api.dtos.ConsultaDTO;
import br.com.vestaplan.api.dtos.ConsultaUpdateDTO;
import br.com.vestaplan.api.entity.Consulta;
import br.com.vestaplan.api.entity.Dentista;
import br.com.vestaplan.api.entity.Paciente;
import br.com.vestaplan.api.entity.Usuario;
import br.com.vestaplan.api.enums.PerfilUsuario;
import br.com.vestaplan.api.enums.StatusConsulta;
import br.com.vestaplan.api.exceptions.EntidadeNaoEncontradaException;
import br.com.vestaplan.api.exceptions.NegocioException;
import br.com.vestaplan.api.mappers.ConsultaMapper;
import br.com.vestaplan.api.repositories.ConsultaRepository;
import br.com.vestaplan.api.repositories.DentistaRepository;
import br.com.vestaplan.api.repositories.PacienteRepository;
import br.com.vestaplan.api.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Paciente não encontrado!"));
        Dentista dentista = dentistaRepository.findById(dto.idDentista())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Dentista não encontrado!"));
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado!"));

        validarRegrasDeHorario(dto.idDentista(), dto.dataInicio(), dto.dataFim(), null);

        Consulta consultaEntity = consultaMapper.toEntity(dto);

        consultaEntity.setPaciente(paciente);
        consultaEntity.setDentista(dentista);
        consultaEntity.setUsuario(usuario);

        return consultaRepository.save(consultaEntity);
    }

    public List<Consulta> getConsultas(){

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (usuarioLogado.getPerfilUsuario() == PerfilUsuario.ADMIN) {
            return consultaRepository.findAll();
        }

        return consultaRepository.findByDentistaEmail(usuarioLogado.getEmail());
    }

    public Consulta findById(Integer id){
        return consultaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Consulta não encontrada!"));
    }

    @Transactional
    public Consulta update(Integer id, ConsultaUpdateDTO dto){

        Consulta atual = consultaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com o ID " + id + " não foi encontrado!"));

        Paciente paciente = pacienteRepository.findById(dto.idPaciente())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Paciente não encontrado!"));
        Dentista dentista = dentistaRepository.findById(dto.idDentista())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Dentista não encontrado!"));
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado!"));

        validarRegrasDeHorario(dto.idDentista(), dto.dataInicio(), dto.dataFim(), id);

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
            throw new EntidadeNaoEncontradaException("Consulta não encontrada para exclusão!");
        }
        consultaRepository.deleteById(id);
    }

    @Transactional
    public Consulta cancelarConsulta(Integer id, ConsultaCancelamentoDTO dto) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Consulta com o ID " + id + " não foi encontrada!"));

        if(consulta.getStatus() == StatusConsulta.FINALIZADA){
            throw new NegocioException("Não é possível cancelar uma consulta que já foi finalizada!");
        }

        if(consulta.getStatus() == StatusConsulta.CANCELADA){
            throw new NegocioException("Esta consulta já está cancelada!");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        consulta.setMotivoCancelamento(dto.motivoCancelamento());

        return consultaRepository.save(consulta);

    }

    public List<Consulta> findConsultasFiltradas(Integer idPaciente, Integer idUsuario, Integer idEspecialidade, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return consultaRepository.findConsultasFiltradas(idPaciente, idUsuario, idEspecialidade, dataInicio, dataFim);
    }

    //Método validar regra de negócio
    private void validarRegrasDeHorario(Integer idDentista, LocalDateTime inicio, LocalDateTime fim, Integer idConsulta) {

        //Não permitir agendamento em datas passadas
        if (inicio.isBefore(LocalDateTime.now())) {
            throw new NegocioException("Não é possível agendar ou alterar uma consulta para datas/horários passados.");
        }

        //O horário final da consulta deve ser após o horário inicial
        if (!fim.isAfter(inicio)) {
            throw new NegocioException("O horário final da consulta deve ser após o horário inicial.");
        }

        //Não permitir conflito de horário para o mesmo dentista
        boolean dentistaOcupado = consultaRepository.existsByDentistaIdAndHorarioConflitante(
                idDentista, inicio, fim, idConsulta
        );
        if (dentistaOcupado) {
            throw new NegocioException("O dentista já possui uma consulta ativa agendada neste horário.");
        }
    }

}
